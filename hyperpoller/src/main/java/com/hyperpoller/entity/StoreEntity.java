package com.hyperpoller.entity;

import com.hyperpoller.model.Store;
import com.hyperpoller.entity.utils.DatabaseTablesConstants;

public class StoreEntity extends BaseEntity {
    private String address;
    private String name;
    private int company_id;

    public StoreEntity(Store store, int companyID) {
        super();
        this.address = store.getAddress();
        this.name = store.getName();
        this.company_id = companyID;
    }

    public StoreEntity() {
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

    public Integer getCompanyId() {
        return company_id;
    }

    public void setCompanyId(int companyId) {
        this.company_id = companyId;
    }

    @Override
    public String getTableName() {
        return DatabaseTablesConstants.STORE_TABLE_NAME;
    }
}
