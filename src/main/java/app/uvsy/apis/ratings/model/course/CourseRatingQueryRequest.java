package app.uvsy.apis.ratings.model.course;

import lombok.Getter;

import java.util.List;

@Getter
public class CourseRatingQueryRequest {
    private final List<String> coursesId;

    public CourseRatingQueryRequest(List<String> subjectsId) {
        this.coursesId = subjectsId;
    }
}
