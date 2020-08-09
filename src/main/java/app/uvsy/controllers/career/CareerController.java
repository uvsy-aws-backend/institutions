package app.uvsy.controllers.career;

import app.uvsy.controllers.career.payload.CreateProgramPayload;
import app.uvsy.controllers.career.payload.UpdateCareerPayload;
import app.uvsy.response.Response;
import app.uvsy.service.CareerService;
import org.github.serverless.api.annotations.HttpMethod;
import org.github.serverless.api.annotations.handler.Handler;
import org.github.serverless.api.annotations.parameters.BodyParameter;
import org.github.serverless.api.annotations.parameters.PathParameter;
import org.github.serverless.api.annotations.parameters.QueryParameter;

import java.util.Optional;

public class CareerController {

    private final CareerService careerService;

    public CareerController() {
        this.careerService = new CareerService();
    }

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/careers/{id}")
    public Response getCareer(@PathParameter(name = "id") String careerId) {
        return Response.of(careerService.getCareer(careerId));
    }

    @Handler(method = HttpMethod.PUT, resource = "/v1/careers/{id}")
    public void updateCareer(@PathParameter(name = "id") String careerId, @BodyParameter UpdateCareerPayload body) {
        careerService.updateCareer(careerId, body.getName(), body.getCodename());
    }

    @Handler(method = HttpMethod.DELETE, resource = "/v1/careers/{id}")
    public void deleteCareer(@PathParameter(name = "id") String careerId) {
        careerService.deleteCareer(careerId);
    }

    @Handler(method = HttpMethod.POST, resource = "/v1/careers/{id}/status")
    public void activateCareer(@PathParameter(name = "id") String careerId,
                               @QueryParameter(name = "active") Boolean active) {
        careerService.updateCareerActive(careerId, active);
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/careers/{id}/programs")
    public Response getPrograms(@PathParameter(name = "id") String careerId,
                                @QueryParameter(name = "only_active", required = false) Boolean onlyActive,
                                @QueryParameter(name = "year", required = false) Integer year) {
        if (year != null) {
            return Response.of(careerService.getProgramsForYear(
                    careerId,
                    Optional.ofNullable(onlyActive).orElse(Boolean.FALSE),
                    year
            ));
        }
        return Response.of(careerService.getPrograms(
                careerId,
                Optional.ofNullable(onlyActive).orElse(Boolean.FALSE)
        ));
    }

    @Handler(method = HttpMethod.POST, resource = "/v1/careers/{id}/programs")
    public void createProgram(@PathParameter(name = "id") String careerId, @BodyParameter CreateProgramPayload payload) {
        careerService.createProgram(
                careerId,
                payload.getName(),
                payload.getYearFrom(),
                payload.getYearTo(),
                payload.getHours(),
                payload.getPoints(),
                payload.getAmountOfSubjects()
        );
    }
}
