package app.uvsy.model.reports.institution;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramStats {
    private String programId;
    private String programName;
    private double rating;
    private int amountOfOptatives;
    private int amountOfSubjects;
}
