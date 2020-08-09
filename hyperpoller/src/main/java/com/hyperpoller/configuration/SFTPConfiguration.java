package com.hyperpoller.configuration;

import com.hyperpoller.configuration.utils.SFTPConfigurationConstants;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import jdk.internal.joptsimple.internal.Strings;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class SFTPConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(SFTPConfiguration.class);
    private Properties properties;

    public SFTPConfiguration() {
        properties = getSFTPProperties();
    }

    public String getHost() {
        return properties.get(SFTPConfigurationConstants.HOST).toString();
    }

    public String getPassword() {
        return properties.get(SFTPConfigurationConstants.PASSWORD).toString();
    }

    public String getUsername() {
        return properties.get(SFTPConfigurationConstants.USERNAME).toString();
    }

    public String getRemoteDirectoryPath() {
        return properties.get(SFTPConfigurationConstants.REMOTE_DIRECTORY).toString();
    }

    public String getLocalDirectoryPath() {
        return properties.get(SFTPConfigurationConstants.LOCAL_DIRECTORY).toString();
    }

    public Integer getPort() {
        return Integer.parseInt((String) properties.get(SFTPConfigurationConstants.PORT));
    }

    private Properties getSFTPProperties() {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader()
                .getResource(Strings.EMPTY)).getPath();
        String SFTPConfigurationPath = rootPath + SFTPConfigurationConstants.SFTP_PROPERTIES_FILE_NAME;
        Properties sftpProperties = new Properties();
        try {
            sftpProperties.load(new FileInputStream(SFTPConfigurationPath));
        } catch (IOException e) {
            logger.error("An error occurred while reading the sftp properties {}", e.getMessage());
        }
        return sftpProperties;
    }
}
