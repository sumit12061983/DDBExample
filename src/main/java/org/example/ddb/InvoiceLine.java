package org.example.ddb;

import java.util.Map;

public class InvoiceLine {
    private InvoiceLineIdentifier invoiceLineIdentifier;
    private Map<String, String> properties;
    private Tax tax;

    public InvoiceLine(InvoiceLineIdentifier invoiceLineIdentifier, Map<String, String> properties, Tax tax) {
        this.invoiceLineIdentifier = invoiceLineIdentifier;
        this.properties = properties;
        this.tax = tax;
    }

    public InvoiceLineIdentifier getInvoiceLineIdentifier() {
        return invoiceLineIdentifier;
    }

    public void setInvoiceLineIdentifier(InvoiceLineIdentifier invoiceLineIdentifier) {
        this.invoiceLineIdentifier = invoiceLineIdentifier;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Tax getTax() {
        return tax;
    }
}
