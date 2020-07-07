package app.uvsy.controllers.course.payload;

import app.uvsy.model.course.CoursingPeriod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;

@ToString
@Getter
public class UpdateCoursePayload {

    private final List<CoursingPeriod> periods;
    private final Boolean active;

    public UpdateCoursePayload(@JsonProperty(value = "periods", required = true) List<CoursingPeriod> periods,
                               @JsonProperty(value = "active") Boolean active) {
        this.periods = periods;
        this.active = Optional.ofNullable(active).orElse(Boolean.TRUE);
    }
}
