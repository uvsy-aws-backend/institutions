package app.uvsy.model.reports.program;

import lombok.Getter;

@Getter
public class SubjectStats {
    private final String subjectId;
    private final String subjectName;
    private final double rating;
    private final boolean optative;

    public SubjectStats(String subjectId,
                        String subjectName,
                        double rating,
                        boolean optative) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.rating = rating;
        this.optative = optative;
    }
}
