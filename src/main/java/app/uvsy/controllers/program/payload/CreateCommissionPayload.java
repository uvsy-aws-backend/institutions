package app.uvsy.controllers.program.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateCommissionPayload {

    private final String name;
    private final Integer level;


    public CreateCommissionPayload(@JsonProperty(value = "name", required = true) String name,
                                   @JsonProperty(value = "level", required = true) Integer level) {
        this.name = name;
        this.level = level;
    }
}
