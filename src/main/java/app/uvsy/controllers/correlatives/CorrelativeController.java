package app.uvsy.controllers.correlatives;

import app.uvsy.controllers.correlatives.payload.UpdateCorrelativePayload;
import app.uvsy.response.Response;
import app.uvsy.service.CorrelativeService;
import org.github.serverless.api.annotations.HttpMethod;
import org.github.serverless.api.annotations.handler.Handler;
import org.github.serverless.api.annotations.parameters.BodyParameter;
import org.github.serverless.api.annotations.parameters.PathParameter;

public class CorrelativeController {

    private final CorrelativeService correlativeService;

    public CorrelativeController() {
        this.correlativeService = new CorrelativeService();
    }

    public CorrelativeController(CorrelativeService correlativeService) {
        this.correlativeService = correlativeService;
    }

    @Handler(method = HttpMethod.GET, resource = "/correlatives/{id}")
    public Response getCorrelative(@PathParameter(name = "id") String correlativeId) {
        return Response.of(correlativeService.getCorrelative(correlativeId));
    }

    @Handler(method = HttpMethod.PUT, resource = "/correlatives/{id}")
    public void updateCorrelative(@PathParameter(name = "id") String correlativeId, @BodyParameter UpdateCorrelativePayload payload) {
        correlativeService.updateCorrelative(
                correlativeId,
                payload.getCorrelativeSubjectId(),
                payload.getCorrelativeRestriction(),
                payload.getCorrelativeCondition()
        );
    }

    @Handler(method = HttpMethod.DELETE, resource = "/correlatives/{id}")
    public void deleteCorrelative(@PathParameter(name = "id") String correlativeId) {
        correlativeService.deleteCorrelative(correlativeId);
    }
}
