package app.uvsy.model;

import app.uvsy.model.course.CoursingPeriod;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGenerateStrategy;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBGeneratedUuid;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@ToString
@Getter
@Setter
@DynamoDBTable(tableName = "courses")
public class Course {

    @DynamoDBHashKey(attributeName = "commission_id")
    private String commissionId;

    @DynamoDBRangeKey(attributeName = "subject_id")
    private String subjectId;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private List<CoursingPeriod> periods;

    @DynamoDBAttribute
    private Boolean active;

    @DynamoDBIgnore
    public boolean isActive() {
        return Optional.ofNullable(active).orElse(Boolean.FALSE);
    }

    @DynamoDBIgnore
    public void activate() {
        this.active = Boolean.TRUE;
    }


}
