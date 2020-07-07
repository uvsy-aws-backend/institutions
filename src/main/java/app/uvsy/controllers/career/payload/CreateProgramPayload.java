package app.uvsy.controllers.career.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;
import java.util.Optional;

@Getter
public class CreateProgramPayload {

    private final String name;
    private final Date validFrom;
    private final Date validTo;
    private final Integer hours;
    private final Integer points;
    private final Integer amountOfSubjects;


    public CreateProgramPayload(@JsonProperty(value = "name", required = true) String name,
                                @JsonProperty(value = "validFrom", required = true) Date validFrom,
                                @JsonProperty(value = "validTo") Date validTo,
                                @JsonProperty(value = "hours") Integer hours,
                                @JsonProperty(value = "points") Integer points,
                                @JsonProperty(value = "amountOfSubjects") Integer amountOfSubjects) {
        this.name = name;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.hours = Optional.ofNullable(hours).orElse(0);
        this.points = Optional.ofNullable(points).orElse(0);
        this.amountOfSubjects = Optional.ofNullable(amountOfSubjects).orElse(0);
    }
}
