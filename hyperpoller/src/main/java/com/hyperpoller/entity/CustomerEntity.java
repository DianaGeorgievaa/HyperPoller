package com.hyperpoller.entity;

import com.hyperpoller.model.Customer;
import com.hyperpoller.entity.utils.DatabaseTablesConstants;

public class CustomerEntity extends BaseEntity {
    private String address;
    private String name;
    private String uid;

    public CustomerEntity(Customer customer) {
        super();
        this.address = customer.getAddress();
        this.name = customer.getName();
        this.uid = customer.getUuid();
    }

    public CustomerEntity() {
        super();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String getTableName() {
        return DatabaseTablesConstants.CUSTOMER_TABLE_NAME;
    }
}
