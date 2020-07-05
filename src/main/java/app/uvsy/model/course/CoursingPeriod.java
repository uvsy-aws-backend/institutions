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

    @DynamoDBAttribute(attributeName = "schedules")
    private List<Schedule> schedules;

    @DynamoDBAttribute(attributeName = "professors")
    private List<Professor> professors;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "begin_month")
    private Month beginMonth;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute(attributeName = "end_month")
    private Month endMonth;
}
