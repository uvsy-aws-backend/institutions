package app.uvsy.controllers.institution;

import app.uvsy.controllers.institution.payload.CreateCareerPayload;
import app.uvsy.controllers.institution.payload.CreateInstitutionPayload;
import app.uvsy.controllers.institution.payload.UpdateInstitutionPayload;
import app.uvsy.response.Response;
import app.uvsy.service.InstitutionService;
import org.github.serverless.api.annotations.HttpMethod;
import org.github.serverless.api.annotations.handler.Handler;
import org.github.serverless.api.annotations.parameters.BodyParameter;
import org.github.serverless.api.annotations.parameters.PathParameter;
import org.github.serverless.api.annotations.parameters.QueryParameter;

import java.util.Optional;

public class InstitutionController {

    private final InstitutionService institutionService;

    public InstitutionController() {
        this.institutionService = new InstitutionService();
    }

    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/institutions")
    public Response getAllInstitutions(@QueryParameter(name = "only_active", required = false) Boolean onlyActive) {
        return Response.of(institutionService.getAll(Optional.ofNullable(onlyActive).orElse(Boolean.FALSE)));
    }

    @Handler(method = HttpMethod.POST, resource = "/v1/institutions")
    public void createInstitution(@BodyParameter CreateInstitutionPayload body) {
        institutionService.createInstitution(body.getName(), body.getCodename());
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/institutions/{id}")
    public Response getInstitution(@PathParameter(name = "id") String institutionId) {
        return Response.of(institutionService.getInstitution(institutionId));
    }

    @Handler(method = HttpMethod.PUT, resource = "/v1/institutions/{id}")
    public void updateInstitution(@PathParameter(name = "id") String institutionId,
                                  @BodyParameter UpdateInstitutionPayload body) {
        institutionService.updateInstitution(
                institutionId,
                body.getName(),
                body.getCodename()
        );
    }

    @Handler(method = HttpMethod.DELETE, resource = "/v1/institutions/{id}")
    public void deleteInstitution(@PathParameter(name = "id") String institutionId) {
        institutionService.deleteInstitution(institutionId);
    }

    @Handler(method = HttpMethod.POST, resource = "/v1/institutions/{id}/activate")
    public void activateInstitution(@PathParameter(name = "id") String institutionId) {
        institutionService.activateInstitution(institutionId);
    }

    @Handler(method = HttpMethod.POST, resource = "/v1/institutions/{id}/careers")
    public void createCareer(@PathParameter(name = "id") String institutionId,
                             @BodyParameter CreateCareerPayload careerPayload) {
        institutionService.createCareer(institutionId, careerPayload.getName(), careerPayload.getCodename());
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/institutions/{id}/careers")
    public Response getCareers(@PathParameter(name = "id") String institutionId,
                               @QueryParameter(name = "only_active", required = false) Boolean onlyActive) {

        return Response.of(institutionService.getCareers(
                institutionId,
                Optional.ofNullable(onlyActive).orElse(Boolean.FALSE)
        ));
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/institutions/{id}/report")
    public Response getInstitutionReport(@PathParameter(name = "id") String institutionId)  {
        return Response.of(institutionService.getReport(
                institutionId
        ));
    }
}
