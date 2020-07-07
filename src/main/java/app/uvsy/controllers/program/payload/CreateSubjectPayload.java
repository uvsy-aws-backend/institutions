package app.uvsy.controllers.program.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Optional;

@Getter
public class CreateSubjectPayload {

    private final String name;
    private final String codename;
    private final Integer level;
    private final Integer hours;
    private final Integer points;
    private final Boolean optative;


    public CreateSubjectPayload(@JsonProperty(value = "name", required = true) String name,
                                @JsonProperty(value = "codename", required = true) String codename,
                                @JsonProperty(value = "level", required = true) Integer level,
                                @JsonProperty(value = "hours") Integer hours,
                                @JsonProperty(value = "points") Integer points,
                                @JsonProperty(value = "optative") Boolean optative) {
        this.name = name;
        this.level = level;
        this.codename = Optional.ofNullable(codename).orElse("");
        this.hours = Optional.ofNullable(hours).orElse(0);
        this.points = Optional.ofNullable(points).orElse(0);
        this.optative = Optional.ofNullable(optative).orElse(Boolean.FALSE);
    }
}
