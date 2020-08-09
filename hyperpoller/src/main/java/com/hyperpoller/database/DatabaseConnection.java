package com.hyperpoller.database;

import com.hyperpoller.exception.HyperPollerException;
import com.hyperpoller.configuration.DatabaseConfiguration;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private DatabaseConfiguration databaseConfiguration;

    public DatabaseConnection() {
        databaseConfiguration = new DatabaseConfiguration();
    }

    public Connection getConnection() {
        Connection connection;
        try {
            Class.forName(databaseConfiguration.getDriver());
            connection = DriverManager.getConnection(databaseConfiguration.getUrl(),
                    databaseConfiguration.getUser(), databaseConfiguration.getPassword());
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("An error occurred while establishing connection with the database");
            throw new HyperPollerException("Failed to establish connection with the database");
        }
        return connection;
    }

    public void createTables() {
        Connection connection = getConnection();
        ScriptRunner runner = new ScriptRunner(connection);
        try (Reader reader = new InputStreamReader(new FileInputStream(databaseConfiguration.getScriptPath()))) {
            runner.runScript(reader);
        } catch (IOException e) {
            logger.error("An error occurred while creating tables");
            throw new HyperPollerException("Failed to create tables");
        }
    }
}