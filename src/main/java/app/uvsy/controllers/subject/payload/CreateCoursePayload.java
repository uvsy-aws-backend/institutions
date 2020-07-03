package app.uvsy.controllers.subject.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateCoursePayload {
    private final String name;


    public CreateCoursePayload(@JsonProperty(value = "name", required = true) String name) {
        this.name = name;
    }
}
