package app.uvsy.controllers.program.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;

@Getter
public class UpdateProgramPayload {

    private final String name;
    private final Date validFrom;
    private final Date validTo;
    private final Integer hours;
    private final Integer points;
    private final Integer amountOfSubjects;


    public UpdateProgramPayload(@JsonProperty(value = "name", required = true) String name,
                                @JsonProperty(value = "validFrom", required = true) Date validFrom,
                                @JsonProperty(value = "validTo", required = true) Date validTo,
                                @JsonProperty(value = "hours", required = true) Integer hours,
                                @JsonProperty(value = "points", required = true) Integer points,
                                @JsonProperty(value = "amountOfSubjects", required = true) Integer amountOfSubjects) {
        this.name = name;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.hours = hours;
        this.points = points;
        this.amountOfSubjects = amountOfSubjects;
    }
}
