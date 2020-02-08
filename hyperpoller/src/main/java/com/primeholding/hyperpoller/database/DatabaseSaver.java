package com.primeholding.hyperpoller.database;

import com.primeholding.hyperpoller.entities.*;
import com.primeholding.hyperpoller.repository.*;
import com.primeholding.hyperpoller.model.Company;
import com.primeholding.hyperpoller.model.Invoice;
import com.primeholding.hyperpoller.model.Receipt;
import com.primeholding.hyperpoller.model.Store;
import com.primeholding.hyperpoller.service.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DatabaseSaver {
    private XMLParser parser;
    private BaseRepository baseRepository;

    public DatabaseSaver() throws IOException {
        parser = new XMLParser();
        baseRepository = new BaseRepository();
    }

    public void saveToDatabase() throws JAXBException, IOException, SQLException, ClassNotFoundException, IllegalAccessException {
        List<Company> companies = parser.getParsedAllXMLFiles();
        int companyId;
        CompanyEntity companyEntity;

        for (Company company : companies) {
            companyEntity = new CompanyEntity(company);
            baseRepository.insert(companyEntity);
            companyId = baseRepository.getId();
            saveStore(company, companyId);
        }
    }

    private void saveStore(Company company, int companyId) throws ClassNotFoundException, IOException, SQLException, JAXBException, IllegalAccessException {
        int storeId;
        StoreEntity storeEntity;

        for (Store store : company.getStores()) {
            storeEntity = new StoreEntity(store, companyId);
            baseRepository.insert(storeEntity);
            storeId = storeEntity.getId();

            saveReceipt(store, storeId);
            saveInvoice(store, storeId);
        }
    }

    private void saveReceipt(Store store, int storeId) throws ClassNotFoundException, IOException, SQLException, JAXBException, IllegalAccessException {
        int cardDetailsId;
        CardDetailsEntity cardDetailsEntity;
        ReceiptEntity receiptEntity;

        for (Receipt receipt : store.getReceipts()) {
            if (receipt.getCardDetails() != null) {
                cardDetailsEntity = new CardDetailsEntity(receipt.getCardDetails());
                baseRepository.insert(cardDetailsEntity);
                cardDetailsId = cardDetailsEntity.getId();
                receiptEntity = new ReceiptEntity(receipt, cardDetailsId, storeId);
                baseRepository.insert(receiptEntity);
            } else {
                receiptEntity = new ReceiptEntity(receipt, null, storeId);
                baseRepository.insert(receiptEntity);
            }
        }
    }

    private void saveInvoice(Store store, int storeId) throws ClassNotFoundException, IOException, SQLException, JAXBException, IllegalAccessException {
        int customerId;
        Integer cardDetailsId;
        CustomerEntity customerEntity;
        CardDetailsEntity cardDetailsEntity;
        InvoiceEntity invoiceEntity;

        for (Invoice invoice : store.getInvoices()) {
            customerEntity = new CustomerEntity(invoice.getCustomer());
            baseRepository.insert(customerEntity);
            customerId = customerEntity.getId();

            if (invoice.getCardDetails() != null) {
                cardDetailsEntity = new CardDetailsEntity(invoice.getCardDetails());
                baseRepository.insert(cardDetailsEntity);
                cardDetailsId = cardDetailsEntity.getId();

                invoiceEntity = new InvoiceEntity(invoice, customerId, cardDetailsId, storeId);
                baseRepository.insert(invoiceEntity);
            } else {
                invoiceEntity = new InvoiceEntity(invoice, customerId, null, storeId);
                baseRepository.insert(invoiceEntity);
            }
        }
    }
}
