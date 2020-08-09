package com.hyperpoller.model;

import java.util.List;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;

@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "address")
    private String address;
    @XmlAttribute(name = "uuid")
    private String uuid;

    @XmlElementWrapper(name = "stores")
    @XmlElement(name = "store")
    private List<Store> stores;

    public Company() {
        stores = new ArrayList<>();
    }

    public Company(String name, String address, String uuid, List<Store> stores) {
        this.name = name;
        this.address = address;
        this.uuid = uuid;
        this.stores = stores;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", uuid=" + uuid +
                ", stores=" + stores.toString() +
                '}';
    }
}
