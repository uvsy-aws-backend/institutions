package app.uvsy.apis.ratings.model;

import lombok.Getter;

import java.util.List;

@Getter
public class SubjectRatingQueryRequest {
    private final List<String> subjectsId;

    public SubjectRatingQueryRequest(List<String> subjectsId) {
        this.subjectsId = subjectsId;
    }
}
