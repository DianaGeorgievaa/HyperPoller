package com.hyperpoller.entity;

import com.hyperpoller.model.Company;
import com.hyperpoller.entity.utils.DatabaseTablesConstants;

public class CompanyEntity extends BaseEntity {
    private String address;
    private String name;
    private String uid;

    public CompanyEntity(Company company) {
        super();
        this.name = company.getName();
        this.address = company.getAddress();
        this.uid = company.getUuid();
    }

    public CompanyEntity() {
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
        return DatabaseTablesConstants.COMPANY_TABLE_NAME;
    }
}
