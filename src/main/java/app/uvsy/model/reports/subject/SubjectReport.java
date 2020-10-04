package app.uvsy.model.reports.subject;

import lombok.Getter;

import java.util.List;

@Getter
public class SubjectReport {

    private final List<CourseStats> courses;

    public SubjectReport(List<CourseStats> courses) {
        this.courses = courses;
    }
}
