package app.uvsy.model.course.professors;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@DynamoDBDocument
public class Professor {

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String lastName;
}
