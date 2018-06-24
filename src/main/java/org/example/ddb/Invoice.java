package org.example.ddb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Map;

@DynamoDBTable(tableName = "Invoices")
public class Invoice {

    @DynamoDBHashKey
    @DynamoDBAttribute(attributeName = "invoiceId")
    private String uniqueId;
    @DynamoDBAttribute
    private InvoiceIdentifier invoiceIdentifier;
    @DynamoDBAttribute(attributeName = "invoiceProperties")
    private Map<String,String> properties;

    public Invoice() {

    }

    public Invoice(InvoiceIdentifier invoiceIdentifier, Map<String, String> properties) {
        this.invoiceIdentifier = invoiceIdentifier;
        this.properties = properties;
        uniqueId = invoiceIdentifier.getUniqueId();
    }

    public InvoiceIdentifier getInvoiceIdentifier() {
        return invoiceIdentifier;
    }

    public void setInvoiceIdentifier(InvoiceIdentifier invoiceIdentifier) {
        this.invoiceIdentifier = invoiceIdentifier;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @DynamoDBIgnore
    @Override public String toString() {
        return "Invoice{" + "uniqueId='" + uniqueId + '\'' + ", invoiceIdentifier=" + invoiceIdentifier
                + ", properties=" + properties + '}';
    }
}
