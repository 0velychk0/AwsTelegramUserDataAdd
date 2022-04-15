package com.ovelychko;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class MainApplication implements RequestHandler<TelegramUserData, String> {

    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    private final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(client);

    @Override
    public String handleRequest(TelegramUserData data, Context context) {
//        LambdaLogger logger = context.getLogger();
//        logger.log("Incoming event: " + data);

        if (data != null) {
            dynamoDBMapper.save(data);
//            logger.log("Item '" + data.getId() + "' added");
            return "Item '" + data.getId() + "' added";
        } else {
            return "Error in adding user";
        }
    }
}
