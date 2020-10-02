package app.uvsy.model.reports.program;

import lombok.Getter;

import java.util.List;

@Getter
public class ProgramReport {
    private final String programId;
    private final String programName;
    private final double rating;
    private final List<SubjectStats> subjects;

    public ProgramReport(String programId,
                         String programName,
                         double rating,
                         List<SubjectStats> subjects) {
        this.programId = programId;
        this.programName = programName;
        this.rating = rating;
        this.subjects = subjects;
    }
}
