package com.hyperpoller.service;

import com.hyperpoller.model.Company;
import com.hyperpoller.configuration.SFTPConfiguration;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.graalvm.util.CollectionsUtil;

import javax.xml.bind.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XMLParser {
    private static final Logger logger = LoggerFactory.getLogger(XMLParser.class);
    private SFTPConfiguration sftpConfiguration;

    public XMLParser() {
        sftpConfiguration = new SFTPConfiguration();
    }

    public List<Company> getParsedAllXMLFiles() {
        File folder = new File(sftpConfiguration.getLocalDirectoryPath());
        File[] files = folder.listFiles();
        if (files == null){
            return Collections.emptyList();
        }

        List<Company> parsedObjects = new ArrayList<>();
        for (File file : files) {
            Company company = getParsedObject(file);
            parsedObjects.add(company);
        }

        return parsedObjects;
    }

    private Company getParsedObject(File file) {
        Company company = new Company();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            company = (Company) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            logger.error("An error occurred while getting parsed object");
            logger.error(e.getMessage());
        }
        return company;
    }

    private void printParsedObjects() {
        List<Company> companies = getParsedAllXMLFiles();
        for (Company company : companies) {
            System.out.println(company);
        }
    }
}
