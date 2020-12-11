package app.uvsy.controllers.career.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpdateCareerPayload {

    private final String name;
    private final String codename;
    private final Boolean active;


    public UpdateCareerPayload(@JsonProperty(value = "name", required = true) String name,
                               @JsonProperty(value = "codename", required = true) String codename,
                               @JsonProperty(value = "active", required = true) Boolean active) {
        this.name = name;
        this.codename = codename;
        this.active = active;
    }
}
