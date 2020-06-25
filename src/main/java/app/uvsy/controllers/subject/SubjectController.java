package app.uvsy.controllers.subject;

import app.uvsy.controllers.subject.payload.CreateCorrelativePayload;
import app.uvsy.controllers.subject.payload.UpdateSubjectPayload;
import app.uvsy.response.Response;
import app.uvsy.service.SubjectService;
import org.github.serverless.api.annotations.HttpMethod;
import org.github.serverless.api.annotations.handler.Handler;
import org.github.serverless.api.annotations.parameters.BodyParameter;
import org.github.serverless.api.annotations.parameters.PathParameter;

public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController() {
        this.subjectService = new SubjectService();
    }

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Handler(method = HttpMethod.GET, resource = "/subjects/{id}")
    public Response getSubject(@PathParameter(name = "id") String subjectId) {
        return Response.of(subjectService.getSubject(subjectId));
    }

    @Handler(method = HttpMethod.PUT, resource = "/subjects/{id}")
    public void updateSubject(@PathParameter(name = "id") String subjectId, @BodyParameter UpdateSubjectPayload payload) {
        subjectService.updateSubject(
                subjectId,
                payload.getName(),
                payload.getCodename(),
                payload.getLevel(),
                payload.getHours(),
                payload.getPoints(),
                payload.getOptative()
        );
    }

    @Handler(method = HttpMethod.DELETE, resource = "/subjects/{id}")
    public void deleteSubject(@PathParameter(name = "id") String subjectId) {
        subjectService.deleteSubject(subjectId);
    }

    @Handler(method = HttpMethod.POST, resource = "/subjects/{id}/activate")
    public void activateSubject(@PathParameter(name = "id") String subjectId) {
        subjectService.activateSubject(subjectId);
    }

    @Handler(method = HttpMethod.GET, resource = "/subjects/{id}/correlatives")
    public Response getCorrelatives(@PathParameter(name = "id") String subjectId) {
        return Response.of(subjectService.getCorrelatives(subjectId));
    }

    @Handler(method = HttpMethod.POST, resource = "/subjects/{id}/correlatives")
    public void createCorrelative(@PathParameter(name = "id") String subjectId, @BodyParameter CreateCorrelativePayload payload) {
        subjectService.createCorrelative(
                subjectId,
                payload.getCorrelativeSubjectId(),
                payload.getCorrelativeRestriction(),
                payload.getCorrelativeCondition()
        );
    }
}
