package app.uvsy.model.course;

import app.uvsy.model.course.professors.Professor;
import app.uvsy.model.course.schedule.Schedule;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Month;
import java.util.List;

@ToString
@Getter
@Setter
@DynamoDBDocument
public class CoursingPeriod {

    @DynamoDBAttribute
    private List<Schedule> schedules;

    @DynamoDBAttribute
    private List<Professor> professors;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute
    private Month beginMonth;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute
    private Month endMonth;
}
