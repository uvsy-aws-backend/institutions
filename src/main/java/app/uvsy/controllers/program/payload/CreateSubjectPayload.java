package app.uvsy.controllers.program.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;

@Getter
public class CreateSubjectPayload {

    private final String name;
    private final Integer hours;
    private final Integer points;


    public CreateSubjectPayload(@JsonProperty(value = "name", required = true) String name,
                                @JsonProperty(value = "hours", required = true) Integer hours,
                                @JsonProperty(value = "points", required = true) Integer points) {
        this.name = name;
        this.hours = hours;
        this.points = points;
    }
}
