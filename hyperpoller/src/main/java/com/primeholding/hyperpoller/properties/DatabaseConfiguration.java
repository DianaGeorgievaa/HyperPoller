package com.primeholding.hyperpoller.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfiguration {
    private static final String PROPERTY_FILE_NAME = "database.properties";
    private Properties databaseProperties;

    public DatabaseConfiguration() throws IOException {
        databaseProperties = getDatabaseProperty();
    }

    public String getUser() {
        return databaseProperties.get("user").toString();
    }

    public String getPassword() {
        return databaseProperties.get("password").toString();
    }

    public String getDriver() {
        return databaseProperties.get("driver").toString();
    }

    public String getUrl() {
        return databaseProperties.get("url").toString();
    }

    public String getSciptPath() {
        return databaseProperties.get("script").toString();
    }

    private Properties getDatabaseProperty() throws IOException {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String databaseConfigPath = rootPath + PROPERTY_FILE_NAME;

        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(databaseConfigPath));
        return databaseProperties;
    }
}
