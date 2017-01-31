package co.sepulveda.web.controller;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */

import co.sepulveda.web.JoggerFactory;
import java.sql.SQLException;
import java.util.UUID;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import com.elibom.jogger.Jogger;
import com.elibom.jogger.test.JoggerTest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author Carlos Sepulveda
 */
public class BaseTest extends JoggerTest {


    /**
     * This is static because we will reference it from the BeforeSuite method that can run in any class
     */
    private static ConfigurableApplicationContext springContext;

    /**
     * This is static because we will reference it from the BeforeSuite method.
     */
    private static Jogger jogger;

    private JedisPool jedisPool;

    @BeforeSuite
    public void doInit() throws Exception {
        System.setProperty("BASE_ENV", "test");
        System.setProperty("DATABASE_URL", "mysql://testing:testing321@localhost:3306/expenses-manager-test?autoReconnect=true");
        System.setProperty("BASEDIR", "");
        springContext = new ClassPathXmlApplicationContext(getConfigLocations());
        jogger = springContext.getBean("joggerFactory", JoggerFactory.class).create();
    }

    @AfterSuite
    public void doDestroy() {
        System.clearProperty("BASE_ENV");
        springContext.close();
    }

    public void setUp() throws Exception {
        databaseOperation(DatabaseOperation.CLEAN_INSERT, "dbunit/init.xml");
        cleanRedis();
    }

    protected ApplicationContext getSpringContext() {
        return springContext;
    }

    /**
     * Helper method. Erases all Redis data.
     */
    protected void cleanRedis() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.flushAll();
        } finally {
            closeJedis(jedis);
        }
    }

    /**
     * Helper method. Initializes the <code>jedisPool</code> (if not yet initialized) and retrieves a Jedis resource. Use the
     * {@link #closeJedis(Jedis)} method to return the resource.
     *
     * @return a Jedis object.
     */
    protected Jedis getJedis() {

        if (jedisPool == null) {
            jedisPool = (JedisPool) getSpringContext().getBean("jedisPool");
        }

        return jedisPool.getResource();

    }

    /**
     * Helper method. Returns a Jedis resource to the pool.
     *
     * @param jedis the Jedis resource which you want to return.
     */
    protected void closeJedis(Jedis jedis) {
        if (jedis != null) {
            try {
                jedisPool.returnResource(jedis);
            } catch (Exception e) {
            }
        }
    }

    protected void databaseOperation(DatabaseOperation operation, String datasetFile) throws BeansException, SQLException, DatabaseUnitException {
                IDataSet dataSet = new FlatXmlDataSetBuilder().build(Thread.currentThread().getContextClassLoader()
                .getResource(datasetFile));

        IDatabaseConnection connection = null;
        try {
            ReplacementDataSet replacementDataSet = new ReplacementDataSet(dataSet);

            connection = getDatabaseDataSourceConnection();
            connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new MySqlDataTypeFactory());
            connection.getConfig().setFeature(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, Boolean.TRUE);
            connection.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, Boolean.TRUE);
            connection.getConfig().setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, new MySqlMetadataHandler());
            connection.getConnection().prepareStatement("set foreign_key_checks=0").execute();
            operation.execute(connection, replacementDataSet);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.getConnection().prepareStatement("set foreign_key_checks=1").execute();
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private DatabaseDataSourceConnection getDatabaseDataSourceConnection() throws SQLException {
        return new DatabaseDataSourceConnection(getSpringContext().getBean("dataSource", DataSource.class));
    }

    /**
     * Helper method. Creates an authenticated session for the specified user.
     *
     * @param userId the user to which we want to create the authenticated session.
     *
     * @return the id of the session that was created.
     * @throws Exception
     */
    protected String createAuthSession(long userId) throws Exception {

        String sessionId = UUID.randomUUID().toString();

        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset("sessions:" + sessionId, "userId", userId + "");
        } finally {
            closeJedis(jedis);
        }

        return sessionId;
    }

    /**
     * Helper method. Retrieve a value from the session in Redis.
     *
     * @param sessionId the id of the session from which we are retrieving the value.
     * @param property the field that we want to retrieve.
     *
     * @return a String object that contains the value returned by Redis.
     */
    protected String getSessionProperty(String sessionId, String property) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hget("sessions:" + sessionId, property);
        } finally {
            closeJedis(jedis);
        }
    }

    protected void setSessionProperty(String sessionId, String property, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.hset("sessions:" + sessionId, property, value);
        } finally {
            closeJedis(jedis);
        }
    }

    protected String[] getConfigLocations() {
        return new String[] { "core-context.xml", "web-context.xml" };
    }

    @Override
    protected Jogger getJogger() throws Exception {
        return jogger;
    }
}

