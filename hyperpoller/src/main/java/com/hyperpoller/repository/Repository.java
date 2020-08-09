package com.hyperpoller.repository;

import com.hyperpoller.entity.BaseEntity;

import java.sql.SQLException;

public interface Repository<T extends BaseEntity> {

    void insert(T entity) throws IllegalAccessException, SQLException, ClassNotFoundException;
}
