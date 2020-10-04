package app.uvsy.model.reports.subject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CourseStats {
    private String courseId;
    private String commissionName;
    private double rating;
    private double difficulty;
    private double wouldTakeAgain;
}
