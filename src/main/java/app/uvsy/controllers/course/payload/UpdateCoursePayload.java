package app.uvsy.controllers.course.payload;

import app.uvsy.model.course.CoursingPeriod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
public class UpdateCoursePayload {

    private final List<CoursingPeriod> periods;
    private final Boolean active;

    public UpdateCoursePayload(@JsonProperty(value = "periods", required = true) List<CoursingPeriod> periods,
                               @JsonProperty(value = "active", required = true, defaultValue = "true") Boolean active) {
        this.periods = periods;
        this.active = active;
    }
}
