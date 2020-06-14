package app.uvsy.controllers.career.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;

@Getter
public class CreateProgramPayload {

    private final String name;
    private final Date validFrom;
    private final Date validTo;
    private final Integer hours;
    private final Integer points;


    public CreateProgramPayload(@JsonProperty(value = "name", required = true) String name,
                                @JsonProperty(value = "validFrom", required = true) Date validFrom,
                                @JsonProperty(value = "validTo", required = false) Date validTo,
                                @JsonProperty(value = "hours", required = true) Integer hours,
                                @JsonProperty(value = "points", required = true) Integer points) {
        this.name = name;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.hours = hours;
        this.points = points;
    }
}
