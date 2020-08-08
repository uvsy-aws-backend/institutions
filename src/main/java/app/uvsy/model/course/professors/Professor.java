package app.uvsy.model.course.professors;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@DynamoDBDocument
public class Professor {

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "last_name")
    private String lastName;
}
