package com.primeholding.hyperpoller.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "total")
    private double total;
    @XmlElement(name = "datetime")
    private String datetime;
    @XmlElement(name = "payment")
    private String payment;
    @XmlElement(name = "carddetails")
    private CardDetails carddetails;
    @XmlElement(name = "customer")
    private Customer customer;

    public Invoice() {
    }

    public Invoice(double total, String datetime, String payment, CardDetails carddetails, Customer customer) {
        this.total = total;
        this.datetime = datetime;
        this.payment = payment;
        this.carddetails = carddetails;
        this.customer = customer;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public CardDetails getCardDetails() {
        return carddetails;
    }

    public void setCardDetails(CardDetails carddetails) {
        this.carddetails = carddetails;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "total=" + total +
                ", datetime=" + datetime +
                ", payment='" + payment + '\'' +
                ", customer=" + customer +
                '}';
    }
}
