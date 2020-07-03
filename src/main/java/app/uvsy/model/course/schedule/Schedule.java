package app.uvsy.model.course.schedule;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.DayOfWeek;

@ToString
@Getter
@Setter
@DynamoDBDocument
public class Schedule {

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute
    private DayOfWeek dayOfWeek;

    @DynamoDBAttribute
    private int beginTime;

    @DynamoDBAttribute
    private int endTime;

    @DynamoDBAttribute
    private String classroom;
}
