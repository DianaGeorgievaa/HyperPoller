package com.hyperpoller.configuration;

import com.hyperpoller.configuration.utils.DatabaseConfigurationConstants;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import jdk.internal.joptsimple.internal.Strings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class DatabaseConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);
    private Properties properties;

    public DatabaseConfiguration() {
        properties = getDatabaseProperties();
    }

    public String getUser() {
        return properties.get(DatabaseConfigurationConstants.USER).toString();
    }

    public String getPassword() {
        return properties.get(DatabaseConfigurationConstants.PASSWORD).toString();
    }

    public String getDriver() {
        return properties.get(DatabaseConfigurationConstants.DRIVER).toString();
    }

    public String getUrl() {
        return properties.get(DatabaseConfigurationConstants.URL).toString();
    }

    public String getScriptPath() {
        return properties.get(DatabaseConfigurationConstants.SCRIPT).toString();
    }

    private Properties getDatabaseProperties() {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                .getResource(Strings.EMPTY)).getPath();
        String databaseConfigurationPath = rootPath + DatabaseConfigurationConstants.DATABASE_PROPERTIES_FILE_NAME;
        Properties databaseProperties = new Properties();
        try {
            properties.load(new FileInputStream(databaseConfigurationPath));
        } catch (IOException e) {
            logger.error("An error occurred while reading the database properties {}", e.getMessage());
        }
        return databaseProperties;
    }
}
