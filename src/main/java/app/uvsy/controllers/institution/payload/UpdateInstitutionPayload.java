package app.uvsy.controllers.institution.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UpdateInstitutionPayload {

    private final String name;
    private final String codename;

    public UpdateInstitutionPayload(@JsonProperty(value = "name", required = true) String name,
                                    @JsonProperty(value = "codename", required = true) String codename) {
        this.name = name;
        this.codename = codename;
    }
}
