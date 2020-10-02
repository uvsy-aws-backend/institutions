package app.uvsy.apis.ratings;

import app.uvsy.apis.APIClient;
import app.uvsy.apis.exceptions.APIClientException;
import app.uvsy.apis.ratings.model.SubjectRatingQueryRequest;
import app.uvsy.apis.ratings.model.SubjectRatingQueryResult;
import app.uvsy.apis.ratings.responses.SubjectRatingQueryResultResponse;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RatingsAPI {

    private static final String SUBJECT_RATING_RESOURCE = "/v1/ratings/subjects/query";

    private final APIClient client;

    public RatingsAPI(String stage) {
        String host = String.format("ratings-api-%s.compute.universy.app", stage);
        this.client = new APIClient(host);
    }

    public Optional<SubjectRatingQueryResult> postSubjectQuery(List<String> subjectsId, boolean onlyRating) throws APIClientException {
        Map<String, String> params = Collections.singletonMap("onlyRating", Boolean.toString(onlyRating));
        SubjectRatingQueryRequest request = new SubjectRatingQueryRequest(subjectsId);
        return client.post(SUBJECT_RATING_RESOURCE,
                SubjectRatingQueryResultResponse.class,
                params,
                request
        ).map(SubjectRatingQueryResultResponse::getData);
    }

    public Optional<SubjectRatingQueryResult> postSubjectQuery(List<String> subjectsId) throws APIClientException {
        return postSubjectQuery(subjectsId, true);
    }
}
