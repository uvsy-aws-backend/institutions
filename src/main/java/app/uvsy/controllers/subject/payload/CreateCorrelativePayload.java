package app.uvsy.controllers.subject.payload;

import app.uvsy.model.CorrelativeCondition;
import app.uvsy.model.CorrelativeRestriction;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateCorrelativePayload {

    private final String correlativeSubjectId;
    private final CorrelativeCondition correlativeCondition;
    private final CorrelativeRestriction correlativeRestriction;

    public CreateCorrelativePayload(@JsonProperty(value = "correlativeSubjectId", required = true) String correlativeSubjectId,
                                    @JsonProperty(value = "correlativeCondition", required = true) CorrelativeCondition correlativeCondition,
                                    @JsonProperty(value = "correlativeRestriction", required = true) CorrelativeRestriction correlativeRestriction) {
        this.correlativeSubjectId = correlativeSubjectId;
        this.correlativeCondition = correlativeCondition;
        this.correlativeRestriction = correlativeRestriction;
    }
}
