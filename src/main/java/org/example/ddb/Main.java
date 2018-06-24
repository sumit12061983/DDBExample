package org.example.ddb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Main {

    static AmazonDynamoDB      sClient = null;
    static DynamoDBProxyServer sServer = null;
    static DynamoDBMapper mapper = null;

    public static void main(String[] args) {
        Invoice invoice = createInvoice();
        runDynamoDB();
        System.out.print(sClient.listTables());
        createItem(invoice);
        accessItem(invoice);
        shutdownDynamoDB();
    }

    public static Invoice createInvoice() {
        InvoiceIdentifier invoiceIdentifier = new InvoiceIdentifier("DummyClient", "1", "1234");
        Map<String, String> properties = Collections.singletonMap("InvoiceVendor", "Sumit");
        return new Invoice(invoiceIdentifier,properties);
    }

    private static void createAmazonDynamoDBClient() {
        sClient = AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
    }

    private static void createMyTables() {
        //Create task tables
        mapper = new DynamoDBMapper(sClient);
        CreateTableRequest tableRequest = mapper.generateCreateTableRequest(Invoice.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        CreateTableResult createTableResult = sClient.createTable(tableRequest);
        System.out.println(createTableResult);
    }

    public static void shutdownDynamoDB() {
        if(sServer != null) {
            try {
                sServer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void runDynamoDB() {
        //Need to set the SQLite4Java library path to avoid a linker error
        System.setProperty("sqlite4java.library.path", "./build/libs/");

        // Create an in-memory and in-process instance of DynamoDB Local that runs over HTTP
        final String[] localArgs = { "-inMemory" };

        try {

            sServer = ServerRunner.createServerFromCommandLineArgs(localArgs);
            sServer.start();

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        createAmazonDynamoDBClient();
        createMyTables();
    }

    public static void createItem(Invoice invoice) {
        mapper.save(invoice);
    }

    public static void accessItem(Invoice invoice) {
        DynamoDBQueryExpression<Invoice> dynamoDBQueryExpression = new DynamoDBQueryExpression<>();
        Invoice localInvoice = new Invoice();
        localInvoice.setUniqueId(invoice.getUniqueId());
        dynamoDBQueryExpression.withHashKeyValues(localInvoice);
        List<Invoice> list = mapper.query(Invoice.class, dynamoDBQueryExpression);
        list.stream().forEach(System.out::println);

        ObjectMapper objectMapper = new ObjectMapper();
        list.stream()
                .map(invoice1 -> {
                    try {
                        return objectMapper.writeValueAsString(invoice1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } return invoice1;
                })
                .forEach(System.out::println);
    }
}
