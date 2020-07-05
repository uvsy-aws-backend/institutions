package app.uvsy.controllers.comission.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpdateCommissionPayload {

    private final String name;
    private final Integer level;


    public UpdateCommissionPayload(@JsonProperty(value = "name", required = true) String name,
                                   @JsonProperty(value = "level", required = true) Integer level) {
        this.name = name;
        this.level = level;
    }
}
