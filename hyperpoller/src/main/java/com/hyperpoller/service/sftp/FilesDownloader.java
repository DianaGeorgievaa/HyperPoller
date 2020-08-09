package com.hyperpoller.service.sftp;

import com.hyperpoller.configuration.SFTPConfiguration;
import com.hyperpoller.exception.HyperPollerException;
import com.jcraft.jsch.*;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.Properties;
import java.util.Vector;

public class FilesDownloader {
    private static final Logger logger = LoggerFactory.getLogger(FilesDownloader.class);
    private SFTPConfiguration sftpConfiguration;

    public FilesDownloader() {
        sftpConfiguration = new SFTPConfiguration();
    }

    public void downloadFiles() {
        Session session = getSession();
        try {
            ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
            sftpChannel.connect();
            Vector<ChannelSftp.LsEntry> directoryEntries = sftpChannel.ls(sftpConfiguration.getRemoteDirectoryPath());

            for (ChannelSftp.LsEntry file : directoryEntries) {
                String currentFileName = "/" + file.getFilename();
                if (!currentFileName.equals("/.") && !currentFileName.equals("/..")) {
                    sftpChannel.get(sftpConfiguration.getRemoteDirectoryPath() + currentFileName, sftpConfiguration.getLocalDirectoryPath());
                }
            }
            sftpChannel.disconnect();
        } catch (JSchException | SftpException e) {
            logger.error("An error occurred while downloading files");
            logger.error(e.getMessage());
        }

        session.disconnect();
    }

    private Session getSession() {
        Properties configuration = new Properties();
        configuration.put("StrictHostKeyChecking", "no");

        JSch ssh = new JSch();
        Session session;
        try {
            session = ssh.getSession(sftpConfiguration.getUsername(), sftpConfiguration.getHost(), sftpConfiguration.getPort());
            session.setConfig(configuration);
            session.setPassword(sftpConfiguration.getPassword());
            session.connect();
        } catch (JSchException e) {
            logger.error("An error occurred while establishing sftp session");
            throw new HyperPollerException("Failed to establish sftp session");
        }
        return session;
    }
}
