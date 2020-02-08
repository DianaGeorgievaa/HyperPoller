package com.primeholding.hyperpoller.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class SFTPConfiguration {
    private static final String PROPERTY_FILE_NAME = "sftp.properties";
    private Properties sftpProperties;

    public SFTPConfiguration() throws IOException {
        sftpProperties = getSFTPProperty();
    }

    public String getHost() {
        return sftpProperties.get("host").toString();
    }

    public String getPassword() {
        return sftpProperties.get("password").toString();
    }

    public String getUsername() {
        return sftpProperties.get("username").toString();
    }

    public String getRemoteDirectoryPath() {
        return sftpProperties.get("remotedirectory").toString();
    }

    public String getLocalDirectoryPath() {
        return sftpProperties.get("localdirectory").toString();
    }

    public Integer getPort() {
        return Integer.parseInt((String) sftpProperties.get("port"));
    }

    private Properties getSFTPProperty() throws IOException {
        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String sftpConfigPath = rootPath + PROPERTY_FILE_NAME;

        Properties SFTPProperties = new Properties();
        SFTPProperties.load(new FileInputStream(sftpConfigPath));
        return SFTPProperties;
    }
}
