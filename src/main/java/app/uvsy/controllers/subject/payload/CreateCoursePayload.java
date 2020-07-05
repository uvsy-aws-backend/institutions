package app.uvsy.controllers.subject.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateCoursePayload {
    private final String commissionId;


    public CreateCoursePayload(@JsonProperty(value = "commissionId", required = true) String commissionId) {
        this.commissionId = commissionId;
    }
}
