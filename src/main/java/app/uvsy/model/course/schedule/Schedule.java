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
    @DynamoDBAttribute(attributeName = "day_of_week")
    private DayOfWeek dayOfWeek;

    @DynamoDBAttribute(attributeName = "begin_time")
    private int beginTime;

    @DynamoDBAttribute(attributeName = "end_time")
    private int endTime;

    @DynamoDBAttribute(attributeName = "classroom")
    private String classroom;
}
