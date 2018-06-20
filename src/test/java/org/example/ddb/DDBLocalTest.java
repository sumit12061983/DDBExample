package org.example.ddb;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DDBLocalTest {

    static AmazonDynamoDB      sClient = null;
    static DynamoDBProxyServer sServer = null;

    @BeforeClass
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
            Assert.fail(e.getMessage());
            return;
        }

        createAmazonDynamoDBClient();
        createMyTables();
    }

    private static void createAmazonDynamoDBClient() {
        sClient = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();
    }

    private static void createMyTables() {
        //Create task tables
        DynamoDBMapper mapper = new DynamoDBMapper(sClient);
        CreateTableRequest tableRequest = mapper.generateCreateTableRequest(User.class);
        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
        CreateTableResult createTableResult = sClient.createTable(tableRequest);
        System.out.println(createTableResult);
    }

    @AfterClass
    public static void shutdownDynamoDB() {
        if(sServer != null) {
            try {
                sServer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testMyTableName() {
        System.out.print(sClient.listTables());
    }
}
