package app.uvsy.database;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.universy.common.dynamo.DynamoDBMapperFactory;

public class DynamoDBConnection {

    private static DynamoDBMapper dynamoDBMapper;

    public static DynamoDBMapper create() {
        if (dynamoDBMapper == null) {
            dynamoDBMapper = DynamoDBMapperFactory.createMapper();
        }
        return dynamoDBMapper;
    }
}
