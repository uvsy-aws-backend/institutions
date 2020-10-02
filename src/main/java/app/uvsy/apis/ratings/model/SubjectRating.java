package app.uvsy.apis.ratings.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubjectRating {
    private final String subjectId;
    private final double rating;

    public SubjectRating(@JsonProperty(value = "subjectId") String subjectId,
                         @JsonProperty(value = "rating") double rating) {
        this.subjectId = subjectId;
        this.rating = rating;
    }
}
