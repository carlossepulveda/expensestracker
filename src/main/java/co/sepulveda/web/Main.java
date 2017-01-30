package co.sepulveda.web;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Carlos Sepulveda <ca.sepulveda.sanchez@gmail.com>
 */
public class Main {

    public static void main(String [] args) {
        try {
            String[] contextLocations = {
                "classpath*:core-context.xml",
                "classpath*:web-context.xml"
            };
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(contextLocations);
            context.registerShutdownHook();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
