package com.hyperpoller.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "carddetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class CardDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(name = "cardtype")
    private String cardtype;
    @XmlElement(name = "number")
    private String number;
    @XmlElement(name = "contactless")
    private boolean contactless;

    public CardDetails() {
    }

    public CardDetails(String cardType, String number, boolean contactless) {
        this.cardtype = cardType;
        this.number = number;
        this.contactless = contactless;
    }

    public String getCardType() {
        return cardtype;
    }

    public void setCardType(String cardType) {
        this.cardtype = cardType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public boolean isContactless() {
        return contactless;
    }

    public void setContactless(boolean contactless) {
        this.contactless = contactless;
    }

    @Override
    public String toString() {
        return "CardDetails{" +
                "cardType='" + cardtype + '\'' +
                ", number=" + number +
                ", contactless=" + contactless +
                '}';
    }
}
