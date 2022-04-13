package com.ovelychko;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class MainApplication implements RequestHandler<Map<String, String>, String> {

    private final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    private final DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(client);

    public MainApplication() {
        log.info("MainApplication created 1");
    }

    @Override
    public String handleRequest(Map<String, String> event, Context context) {
        LambdaLogger logger = context.getLogger();

        logger.log("Incoming event: " + event);

        TelegramUserData data = new TelegramUserData();
        data.setId(Long.parseLong(event.getOrDefault("id", "0")));
        data.setFirstName(event.getOrDefault("firstName", ""));
        data.setIsBot(Boolean.valueOf(event.getOrDefault("isBot", "false")));
        data.setLastName(event.getOrDefault("lastName", ""));
        data.setUserName(event.getOrDefault("userName", ""));
        data.setLanguageCode(event.getOrDefault("languageCode", "en"));
        data.setCanJoinGroups(Boolean.valueOf(event.getOrDefault("canJoinGroups", "false")));
        data.setCanReadAllGroupMessages(Boolean.valueOf(event.getOrDefault("canReadAllGroupMessages", "false")));
        data.setSupportInlineQueries(Boolean.valueOf(event.getOrDefault("supportInlineQueries", "false")));

        // MAPPER.readValue(event.getBody(), TelegramUserData.class);
        dynamoDBMapper.save(data);

        logger.log("Item '" + data.getId() + "' added");
        return "code: 200 Done";
    }
}
