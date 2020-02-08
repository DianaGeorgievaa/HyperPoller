package com.primeholding.hyperpoller.services;

import com.primeholding.hyperpoller.models.Company;
import com.primeholding.hyperpoller.properties.SFTPConfiguration;

import javax.xml.bind.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {
    private SFTPConfiguration property;

    public XMLParser() throws IOException {
        property = new SFTPConfiguration();
    }

    public List<Company> getParsedAllXMLFiles() {
        File folder = new File(property.getLocalDirectoryPath());
        File[] files = folder.listFiles();

        List<Company> parsedObjects = new ArrayList<>();
        Company company;
        for (File file : files) {
            company = getParsedObject(file);
            parsedObjects.add(company);
        }

        return parsedObjects;
    }

    void printParsedObjects() {
        List<Company> parsedObjects = getParsedAllXMLFiles();
        for (int i = 0; i < parsedObjects.size(); i++) {
            System.out.println(parsedObjects.get(i));
        }
    }

    private Company getParsedObject(File file) {
        Company company = new Company();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            company = (Company) jaxbUnmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return company;
    }
}


