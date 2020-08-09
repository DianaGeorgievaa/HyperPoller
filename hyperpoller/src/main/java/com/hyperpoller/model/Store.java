package com.hyperpoller.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement(name = "store")
@XmlAccessorType(XmlAccessType.FIELD)
public class Store implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "address")
    private String address;

    @XmlElementWrapper(name = "receipts")
    @XmlElement(name = "receipt")
    private List<Receipt> receipts;
    @XmlElementWrapper(name = "invoices")
    @XmlElement(name = "invoice")
    private List<Invoice> invoices;

    public Store() {
        receipts = new ArrayList<>();
        invoices = new ArrayList<>();
    }

    public Store(String name, String address, List<Invoice> invoices, List<Receipt> receipts) {
        this.name = name;
        this.address = address;
        this.receipts = receipts;
        this.invoices = invoices;
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

    public List<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", receipts=" + receipts.toString() +
                ", invoices=" + invoices.toString() +
                '}';
    }
}
