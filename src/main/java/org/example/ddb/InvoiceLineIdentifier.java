package org.example.ddb;

public class InvoiceLineIdentifier {

    private InvoiceIdentifier invoiceIdentifier;
    private String clientId;
    private String invoiceLineId;
    private final String delimiter = "<%>";

    public InvoiceLineIdentifier(InvoiceIdentifier invoiceIdentifier, String clientId, String invoiceLineId) {
        this.invoiceIdentifier = invoiceIdentifier;
        this.clientId = clientId;
        this.invoiceLineId = invoiceLineId;
    }

    public String getUniqueId(){
        return String.join(delimiter, invoiceIdentifier.getUniqueId(),clientId,invoiceLineId);
    }

    public InvoiceIdentifier getInvoiceIdentifier() {
        return invoiceIdentifier;
    }

    public String getClientId() {
        return clientId;
    }

    public String getInvoiceLineId() {
        return invoiceLineId;
    }
}
