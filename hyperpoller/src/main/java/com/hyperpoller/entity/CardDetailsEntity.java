package com.hyperpoller.entity;

import com.hyperpoller.model.CardDetails;
import com.hyperpoller.entity.utils.DatabaseTablesConstants;

public class CardDetailsEntity extends BaseEntity {
    private String card_number;
    private String card_type;
    private Boolean is_contactless;

    public CardDetailsEntity(CardDetails cardDetails) {
        super();
        this.card_number = cardDetails.getNumber();
        this.card_type = cardDetails.getCardType();
        this.is_contactless = cardDetails.isContactless();
    }

    public CardDetailsEntity() {
        super();
    }

    public String getCardNumber() {
        return card_number;
    }

    public void setCardNumber(String cardNumber) {
        this.card_number = cardNumber;
    }

    public String getCardType() {
        return card_type;
    }

    public void setCardType(String cardType) {
        this.card_type = cardType;
    }

    public Boolean isContactless() {
        return is_contactless;
    }

    public void setContactless(boolean contactless) {
        is_contactless = contactless;
    }

    @Override
    public String getTableName() {
        return DatabaseTablesConstants.CARD_DETAILS_TABLE_NAME;
    }
}
