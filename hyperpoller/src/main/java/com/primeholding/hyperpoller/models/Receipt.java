package com.primeholding.hyperpoller.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "receipt")
@XmlAccessorType(XmlAccessType.FIELD)
public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "total")
    private double total;
    @XmlElement(name = "datetime")
    private String datetime;
    @XmlElement(name = "payment")
    private String payment;
    @XmlElement(name = "carddetails")
    private CardDetails carddetails;

    public Receipt() {
    }

    public Receipt(double total, String datetime, String payment, CardDetails cardDetails) {
        this.total = total;
        this.datetime = datetime;
        this.payment = payment;
        this.carddetails = cardDetails;
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

    public void setCardDetails(CardDetails cardDetails) {
        this.carddetails = cardDetails;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "total=" + total +
                ", datetime=" + datetime +
                ", payment='" + payment + '\'' +
                ", cardDetails=" + carddetails +
                '}';
    }
}
