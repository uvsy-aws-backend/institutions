package app.uvsy.apis.ratings.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class SubjectRatingQueryResult {
    private final double rating;
    private final List<SubjectRating> subjectRatings;

    public SubjectRatingQueryResult(@JsonProperty(value = "rating") double rating,
                                    @JsonProperty(value = "subjectRatings") List<SubjectRating> subjectRatings) {
        this.rating = rating;
        this.subjectRatings = subjectRatings;
    }
}
