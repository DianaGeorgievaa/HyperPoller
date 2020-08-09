package com.hyperpoller.database;

import com.hyperpoller.entity.*;
import com.hyperpoller.model.Company;
import com.hyperpoller.model.Invoice;
import com.hyperpoller.model.Receipt;
import com.hyperpoller.model.Store;
import com.hyperpoller.repository.BaseRepository;
import com.hyperpoller.service.XMLParser;

import java.sql.SQLException;
import java.util.List;

public class DatabaseSaver {
    private XMLParser parser;
    private BaseRepository baseRepository;

    public DatabaseSaver() {
        parser = new XMLParser();
        baseRepository = new BaseRepository();
    }

    public void save() throws IllegalAccessException, SQLException, ClassNotFoundException {
        List<Company> companies = parser.getParsedAllXMLFiles();
        for (Company company : companies) {
            CompanyEntity companyEntity = new CompanyEntity(company);
            baseRepository.insert(companyEntity);
            int companyId = companyEntity.getId();
            saveStore(company, companyId);
        }
    }

    private void saveStore(Company company, int companyId) throws IllegalAccessException, SQLException, ClassNotFoundException {
        for (Store store : company.getStores()) {
            StoreEntity storeEntity = new StoreEntity(store, companyId);
            baseRepository.insert(storeEntity);
            int storeId = storeEntity.getId();
            saveReceipt(store, storeId);
            saveInvoice(store, storeId);
        }
    }

    private void saveReceipt(Store store, int storeId) throws IllegalAccessException, SQLException, ClassNotFoundException {
        for (Receipt receipt : store.getReceipts()) {
            ReceiptEntity receiptEntity;
            if (receipt.getCardDetails() != null) {
                CardDetailsEntity cardDetailsEntity = new CardDetailsEntity(receipt.getCardDetails());
                baseRepository.insert(cardDetailsEntity);
                int cardDetailsId = cardDetailsEntity.getId();
                receiptEntity = new ReceiptEntity(receipt, cardDetailsId, storeId);
            } else {
                receiptEntity = new ReceiptEntity(receipt, null, storeId);
            }
            baseRepository.insert(receiptEntity);
        }
    }

    private void saveInvoice(Store store, int storeId) throws IllegalAccessException, SQLException, ClassNotFoundException {
        for (Invoice invoice : store.getInvoices()) {
            InvoiceEntity invoiceEntity;
            CustomerEntity customerEntity = new CustomerEntity(invoice.getCustomer());
            baseRepository.insert(customerEntity);
            int customerId = customerEntity.getId();

            if (invoice.getCardDetails() != null) {
                CardDetailsEntity cardDetailsEntity = new CardDetailsEntity(invoice.getCardDetails());
                baseRepository.insert(cardDetailsEntity);
                int cardDetailsId = cardDetailsEntity.getId();
                invoiceEntity = new InvoiceEntity(invoice, customerId, cardDetailsId, storeId);
            } else {
                invoiceEntity = new InvoiceEntity(invoice, customerId, null, storeId);
            }
            baseRepository.insert(invoiceEntity);
        }
    }
}
