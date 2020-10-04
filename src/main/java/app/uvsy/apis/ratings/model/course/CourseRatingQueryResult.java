package app.uvsy.apis.ratings.model.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CourseRatingQueryResult {
    private final double rating;
    private final List<CourseRating> courseRatings;

    public CourseRatingQueryResult(@JsonProperty(value = "rating") double rating,
                                   @JsonProperty(value = "courseRatings") List<CourseRating> courseRatings) {
        this.rating = rating;
        this.courseRatings = courseRatings;
    }
}
