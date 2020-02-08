package com.primeholding.hyperpoller.entities;

import com.primeholding.hyperpoller.entities.utils.TableUtils;
import com.primeholding.hyperpoller.models.Invoice;

public class InvoiceEntity extends BaseEntity {
    private String payment_type;
    private String invoice_date;
    private Double total_sum;
    private int customer_id;
    private Integer carddetails_id;
    private int store_id;

    public InvoiceEntity(Invoice invoice, int customerId, Integer cardDetailsId, int storeId) {
        super();
        this.payment_type = invoice.getPayment();
        this.invoice_date = invoice.getDatetime();
        this.total_sum = invoice.getTotal();
        this.customer_id = customerId;
        this.carddetails_id = cardDetailsId;
        this.store_id = storeId;
    }

    public InvoiceEntity() {
        super();
    }

    public String getPaymentType() {
        return payment_type;
    }

    public void setPaymentType(String paymentType) {
        this.payment_type = paymentType;
    }

    public String getDate() {
        return invoice_date;
    }

    public void setDate(String date) {
        this.invoice_date = date;
    }

    public double getTotalSum() {
        return total_sum;
    }

    public void setTotalSum(Double totalSum) {
        this.total_sum = totalSum;
    }

    public int getCustomerId() {
        return customer_id;
    }

    public void setCustomerId(int customerId) {
        this.customer_id = customerId;
    }

    public Integer getCardDetailsId() {
        return carddetails_id;
    }

    public void setCardDetailsId(Integer cardDetailsId) {
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
        return TableUtils.INVOICE_TABLE_NAME;
    }
}
