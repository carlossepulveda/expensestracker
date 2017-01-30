package co.sepulveda.util;

import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Uri {
    private final Logger LOG = LoggerFactory.getLogger(Uri.class);

    private URI uri;

    public Uri(String uri) throws URISyntaxException {
        this.uri = new URI(uri);
        LOG.info("New URI found: "+getUrl());
    }

    public String getUrl() {
        return uri.getScheme() + "://" + uri.getHost() + ":" + uri.getPort();
    }

    public String getHost() {
        return uri.getHost();
    }

    public String getScheme() {
        return uri.getScheme();
    }

    public int getPort() {
        return uri.getPort();
    }

    public String getPath() {
        String path = uri.getPath();
        if (uri.getQuery() != null) {
            path += "?" + uri.getQuery();
        }
        return path;
    }

    public String getCleanPath() {
        if (uri.getPath() != null && !uri.getPath().equals("/")) {
            return uri.getPath().substring(1);
        } else {
            return "/";
        }
    }

    public String getUsername() {
        if (uri.getUserInfo() != null) {
            return uri.getUserInfo().split(":", 2)[0];
        }
        return null;
    }

    public String getPassword() {
        if (uri.getUserInfo() != null) {
            return uri.getUserInfo().split(":", 2)[1];
        }
        return null;
    }
}
