package app.uvsy.model.reports.institution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CareerStats {

    private String careerId;
    private String careerName;
    private List<ProgramStats> programs;

    @JsonIgnore
    public double getRating() {
        return programs.stream()
                .mapToDouble(ProgramStats::getRating)
                .average()
                .orElse(0.0);
    }

}
