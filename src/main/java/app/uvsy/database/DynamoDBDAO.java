package app.uvsy.database;

import app.uvsy.database.exceptions.DBException;
import app.uvsy.database.exceptions.ItemDoesNotExistException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.universy.common.dynamo.DynamoDBMapperFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DynamoDBDAO<T> {
    private static DynamoDBMapper dynamoDBMapper;
    private final Class<T> entity;

    private DynamoDBDAO(Class<T> entity) {
        this.entity = entity;
    }

    private DynamoDBMapper getMapper() {
        dynamoDBMapper = Optional.ofNullable(dynamoDBMapper).orElseGet(DynamoDBMapperFactory::createMapper);
        return dynamoDBMapper;
    }

    public static <R> DynamoDBDAO<R> createFor(Class<R> clazz) {
        return new DynamoDBDAO<>(clazz);
    }

    public List<T> get(T object) {
        try {
            DynamoDBQueryExpression<T> query = new DynamoDBQueryExpression<T>().withHashKeyValues(object);
            return getMapper().query(entity, query);
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public void save(T object) {
        try {
            getMapper().save(object);
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public void update(T object, Map<String, String> conditions) {
        try {
            DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
            Map<String, ExpectedAttributeValue> expectedAttributes = conditions
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> new ExpectedAttributeValue(true)
                                    .withValue(new AttributeValue().withS(e.getValue()))
                    ));

            saveExpression.setExpected(expectedAttributes);
            saveExpression.setConditionalOperator(ConditionalOperator.AND);

            getMapper().save(object, saveExpression);
        } catch (ConditionalCheckFailedException e) {
            throw new ItemDoesNotExistException(e);
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    public void deleteCourse(T obj) {
        try {
            getMapper().delete(obj);
        } catch (Exception e) {
            throw new DBException(e);
        }
    }
}
