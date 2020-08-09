package com.hyperpoller.service;

import com.hyperpoller.database.DatabaseConnection;
import com.hyperpoller.database.DatabaseSaver;
import com.hyperpoller.service.sftp.FilesDownloader;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            run();
        } catch (IllegalAccessException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void run() throws IllegalAccessException, SQLException, ClassNotFoundException {
        downloadFiles();
        parseXMLFiles();
        createTables();
        saveObjectsToDataBase();
    }

    private static void downloadFiles() {
        FilesDownloader filesDownloader = new FilesDownloader();
        filesDownloader.downloadFiles();
    }

    private static void parseXMLFiles() {
        XMLParser xmlParser = new XMLParser();
        xmlParser.getParsedAllXMLFiles();
        // parser.printParsedObjects();
    }

    private static void createTables() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.createTables();
    }

    private static void saveObjectsToDataBase() throws IllegalAccessException, SQLException, ClassNotFoundException {
        DatabaseSaver databaseSaver = new DatabaseSaver();
        databaseSaver.save();
    }
}