package app.uvsy.controllers.subject.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpdateSubjectPayload {

    private final String name;
    private final String codename;
    private final Integer hours;
    private final Integer points;
    private final Boolean optative;


    public UpdateSubjectPayload(@JsonProperty(value = "name", required = true) String name,
                                @JsonProperty(value = "codename", required = true) String codename,
                                @JsonProperty(value = "hours", required = true) Integer hours,
                                @JsonProperty(value = "points", required = true) Integer points,
                                @JsonProperty(value = "optative", required = true) Boolean optative) {
        this.name = name;
        this.codename = codename;
        this.hours = hours;
        this.points = points;
        this.optative = optative;
    }
}
