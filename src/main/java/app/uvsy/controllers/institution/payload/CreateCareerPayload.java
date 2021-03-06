package app.uvsy.controllers.institution.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Optional;

@Getter
public class CreateCareerPayload {

    private final String name;
    private final String codename;

    public CreateCareerPayload(@JsonProperty(value = "name", required = true) String name,
                               @JsonProperty(value = "codename") String codename) {
        this.name = name;
        this.codename = Optional.ofNullable(codename).orElse("");
    }
}
