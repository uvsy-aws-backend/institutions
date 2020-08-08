package app.uvsy.controllers.program.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Optional;

@Data
public class UpdateProgramPayload {

    private final String name;
    private final Integer yearFrom;
    private final Integer yearTo;
    private final Integer hours;
    private final Integer points;
    private final Integer amountOfSubjects;


    public UpdateProgramPayload(@JsonProperty(value = "name", required = true) String name,
                                @JsonProperty(value = "yearFrom", required = true) Integer yearFrom,
                                @JsonProperty(value = "yearTo", required = true) Integer yearTo,
                                @JsonProperty(value = "hours") Integer hours,
                                @JsonProperty(value = "points") Integer points,
                                @JsonProperty(value = "amountOfSubjects") Integer amountOfSubjects) {
        this.name = name;
        this.yearFrom = yearFrom;
        this.yearTo = yearTo;
        this.hours = Optional.ofNullable(hours).orElse(0);
        this.points = Optional.ofNullable(points).orElse(0);
        this.amountOfSubjects = Optional.ofNullable(amountOfSubjects).orElse(0);
    }
}
