package app.uvsy.controllers.program.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateProgramPayload {

    @JsonProperty(value = "codename")
    private String codename;
}
