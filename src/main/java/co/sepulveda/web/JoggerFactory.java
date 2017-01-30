package co.sepulveda.web;

import co.sepulveda.web.middleware.ExceptionMiddleware;
import com.elibom.jogger.Jogger;
import com.elibom.jogger.Middleware;
import com.elibom.jogger.MiddlewaresFactory;
import com.elibom.jogger.middleware.router.RouterMiddleware;
import com.elibom.jogger.middleware.router.interceptor.Interceptor;
import com.elibom.jogger.middleware.router.loader.RoutesLoader;
import com.elibom.jogger.middleware.statik.StaticMiddleware;
import com.elibom.jogger.template.FreemarkerTemplateEngine;
import freemarker.template.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class JoggerFactory implements ApplicationContextAware {

    /**
     * Injected. Used to load the interceptors.
     */
    private ApplicationContext applicationContext;

    /**
     * Injected.
     */
    private RoutesLoader routesLoader;

    /**
     * Injected.
     */
    private Configuration freeMarker;

    /**
     * Injected with default.
     */
    private String staticDirectory = "static";

    public Jogger create() throws Exception {
        Jogger app = new Jogger(new MiddlewaresFactory() {

            @Override
            public Middleware[] create() throws Exception {
                RouterMiddleware router = new RouterMiddleware();
                router.setRoutes(routesLoader.load());

                Interceptor securityInterceptor = applicationContext.getBean("securityInterceptor", Interceptor.class);
                router.addInterceptor(securityInterceptor);

                return new Middleware[]{new ExceptionMiddleware(), new StaticMiddleware(staticDirectory, "static"), router};
            }

        });
        app.setTemplateEngine(new FreemarkerTemplateEngine(freeMarker));

        return app;
    }

    public void setRoutesLoader(RoutesLoader routesLoader) {
        this.routesLoader = routesLoader;
    }

    public void setFreeMarker(Configuration freeMarker) {
        this.freeMarker = freeMarker;
    }

    public void setStaticDirectory(String staticDirectory) {
        this.staticDirectory = staticDirectory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
