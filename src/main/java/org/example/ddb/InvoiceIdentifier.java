package org.example.ddb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;

@DynamoDBDocument
public class InvoiceIdentifier {
    @DynamoDBAttribute
    private String clientId;
    @DynamoDBAttribute
    private String version;
    @DynamoDBAttribute
    private String invoiceNumber;
    private final String delimiter = "<%>";

    @DynamoDBIgnore
    public String getUniqueId() {
        return String.join(delimiter,clientId,version,invoiceNumber);
    }

    public InvoiceIdentifier(String clientId, String version, String invoiceNumber) {
        this.clientId = clientId;
        this.version = version;
        this.invoiceNumber = invoiceNumber;
    }

    public InvoiceIdentifier() {

    }

    public String getClientId() {
        return clientId;
    }

    public String getVersion() {
        return version;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @DynamoDBIgnore
    @Override public String toString() {
        return "InvoiceIdentifier{" + "clientId='" + clientId + '\'' + ", version='" + version + '\''
                + ", invoiceNumber='" + invoiceNumber + '\'' + ", delimiter='" + delimiter + '\'' + '}';
    }
}
