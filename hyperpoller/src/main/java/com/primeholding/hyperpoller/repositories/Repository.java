package com.primeholding.hyperpoller.repositories;

import com.primeholding.hyperpoller.entities.BaseEntity;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;

public interface Repository<T extends BaseEntity> {

    public void insert(T entity) throws JAXBException, IOException, ClassNotFoundException, SQLException, IllegalAccessException;
}
