package co.sepulveda.util;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import java.io.File;
import java.io.IOException;

import freemarker.template.Configuration;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreeMarkerFactory {

    private Logger log = LoggerFactory.getLogger(FreeMarkerFactory.class);

    private String defaultEncoding = "UTF-8";

    private String templatesDirectory = "templates/";

    private String classpathDirectory = "/templates";

    public Configuration create() throws IOException {

        Configuration configuration = new Configuration();
        configuration.setDefaultEncoding(defaultEncoding);

        addTemplatesLoader(configuration);

        return configuration;

    }

    private void addTemplatesLoader(Configuration configuration) throws IOException {
        List<TemplateLoader> templatesLoaders = new ArrayList<>();

        ClassTemplateLoader classpathLoader = new ClassTemplateLoader(getClass(), classpathDirectory);
        templatesLoaders.add(classpathLoader);

        File templateDirectory = new File(templatesDirectory);
        if (templateDirectory.exists()) {
            FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(templateDirectory);
            templatesLoaders.add(fileTemplateLoader);
        } else {
            log.warn("Template folder not found [" + templatesDirectory + "]");
        }

        MultiTemplateLoader mtl = new MultiTemplateLoader(templatesLoaders.toArray(new TemplateLoader[templatesLoaders.size()]));
        configuration.setTemplateLoader(mtl); 
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

    public void setTemplatesDirectory(String templatesDirectory) {
        this.templatesDirectory = templatesDirectory;
    }

    public void setClasspathDirectory(String classpathDirectory) {
        this.classpathDirectory = classpathDirectory;
    }
}
