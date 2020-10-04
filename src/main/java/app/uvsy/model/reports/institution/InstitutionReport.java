package app.uvsy.model.reports.institution;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InstitutionReport {

    private final double rating;
    private final List<CareerStats> careers;

    public InstitutionReport(double rating, List<CareerStats> careers) {
        this.rating = rating;
        this.careers = careers;
    }
}
