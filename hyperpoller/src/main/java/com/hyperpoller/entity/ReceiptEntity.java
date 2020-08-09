package com.hyperpoller.entity;

import com.hyperpoller.model.Receipt;
import com.hyperpoller.entity.utils.DatabaseTablesConstants;

public class ReceiptEntity extends BaseEntity {
    private String payment_type;
    private String receipt_date;
    private Double total_sum;

    private Integer carddetails_id;
    private int store_id;

    public ReceiptEntity(Receipt receipt, Integer cardDetailsId, int storeId) {
        super();
        this.payment_type = receipt.getPayment();
        this.receipt_date = receipt.getDatetime();
        this.total_sum = receipt.getTotal();
        this.carddetails_id = cardDetailsId;
        this.store_id = storeId;
    }

    public ReceiptEntity() {
        super();
    }

    public String getPaymentType() {
        return payment_type;
    }

    public void setPaymentType(String paymentType) {
        this.payment_type = paymentType;
    }

    public String getDate() {
        return receipt_date;
    }

    public void setDate(String date) {
        this.receipt_date = date;
    }

    public Double getTotalSum() {
        return total_sum;
    }

    public void setTotalSum(Double totalSum) {
        this.total_sum = totalSum;
    }

    public Integer getCardDetailsId() {
        return carddetails_id;
    }

    public void setCardDetailsId(int cardDetailsId) {
        this.carddetails_id = cardDetailsId;
    }

    public int getStoreId() {
        return store_id;
    }

    public void setStoreId(int storeId) {
        this.store_id = storeId;
    }

    @Override
    public String getTableName() {
        return DatabaseTablesConstants.RECEIPTS_TABLE_NAME;
    }
}
