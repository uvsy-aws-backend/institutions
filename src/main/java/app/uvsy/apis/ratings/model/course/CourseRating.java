package app.uvsy.apis.ratings.model.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseRating {
    private final String courseId;
    private final double overallRating;
    private final double difficultyRating;
    private final double wouldTakeAgainRating;

    public CourseRating(@JsonProperty(value = "courseId") String courseId,
                        @JsonProperty(value = "overallRating") double overallRating,
                        @JsonProperty(value = "difficultyRating") double difficultyRating,
                        @JsonProperty(value = "wouldTakeAgainRating") double wouldTakeAgainRating) {
        this.courseId = courseId;
        this.overallRating = overallRating;
        this.difficultyRating = difficultyRating;
        this.wouldTakeAgainRating = wouldTakeAgainRating;
    }
}
