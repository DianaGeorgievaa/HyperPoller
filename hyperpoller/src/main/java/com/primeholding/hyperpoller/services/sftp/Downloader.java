package com.primeholding.hyperpoller.services.sftp;

import com.jcraft.jsch.*;
import com.primeholding.hyperpoller.properties.SFTPConfiguration;

import java.util.Properties;
import java.io.IOException;
import java.util.Vector;

public class Downloader {
    private SFTPConfiguration property;

    public Downloader() throws IOException {
        property = new SFTPConfiguration();
    }

    public void downloadFile() throws JSchException, SftpException {
        Session session = setUpSession();
        ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
        sftpChannel.connect();

        Vector<ChannelSftp.LsEntry> directoryEntries = sftpChannel.ls(property.getRemoteDirectoryPath());
        String currentFileName = "";
        for (ChannelSftp.LsEntry file : directoryEntries) {
            currentFileName = "/" + file.getFilename();
            if (!currentFileName.equals("/.") && !currentFileName.equals("/..")) {
                sftpChannel.get(property.getRemoteDirectoryPath() + currentFileName, property.getLocalDirectoryPath());
            }
        }

        sftpChannel.disconnect();
        session.disconnect();
    }

    private Session setUpSession() throws JSchException {
        Properties configuration = new Properties();
        configuration.put("StrictHostKeyChecking", "no");

        JSch ssh = new JSch();
        Session session = ssh.getSession(property.getUsername(), property.getHost(), property.getPort());
        session.setConfig(configuration);
        session.setPassword(property.getPassword());
        session.connect();

        return session;
    }
}
