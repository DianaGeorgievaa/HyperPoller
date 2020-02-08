package com.primeholding.hyperpoller.services;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.primeholding.hyperpoller.database.DatabaseConnection;
import com.primeholding.hyperpoller.database.DatabaseSaver;
import com.primeholding.hyperpoller.services.sftp.Downloader;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String args[]) throws IOException, SftpException, JSchException, JAXBException, ClassNotFoundException, SQLException, IllegalAccessException {
        run();
    }

    private static void downloadFiles() throws JSchException, SftpException, IOException {
        Downloader downloader = new Downloader();
        downloader.downloadFile();
    }

    private static void parseXMLFiles() throws JAXBException, IOException {
        XMLParser parser = new XMLParser();
        parser.printParsedObjects();
    }

    private static void establishDBConnection() throws IOException, ClassNotFoundException {
        Connection connection = null;
        DatabaseConnection dbConnection = new DatabaseConnection();
        try {
            connection = dbConnection.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        dbConnection.createTables();
    }

    private static void saveObjectsToDataBase() throws ClassNotFoundException, IOException, IllegalAccessException, SQLException, JAXBException {
        DatabaseSaver saver = new DatabaseSaver();
        saver.saveToDatabase();
    }

    private static void run() throws JSchException, SftpException, IOException, JAXBException, ClassNotFoundException, SQLException, IllegalAccessException {
        downloadFiles();
        parseXMLFiles();
        establishDBConnection();
        saveObjectsToDataBase();
    }
}
