package com.primeholding.hyperpoller.database;

import com.primeholding.hyperpoller.properties.DatabaseConfiguration;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private DatabaseConfiguration databaseConfiguration;

    public DatabaseConnection() throws IOException {
        databaseConfiguration = new DatabaseConfiguration();
    }

    public Connection getConnection() throws ClassNotFoundException {
        Connection connection = null;
        Class.forName(databaseConfiguration.getDriver());
        try {
            connection = DriverManager.getConnection(databaseConfiguration.getUrl(), databaseConfiguration.getUser(), databaseConfiguration.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void createTables() throws IOException, ClassNotFoundException {
        Connection connection = getConnection();
        ScriptRunner runner = new ScriptRunner(connection);
        Reader reader = new InputStreamReader(new FileInputStream(databaseConfiguration.getSciptPath()));

        runner.runScript(reader);
        reader.close();
    }
}
