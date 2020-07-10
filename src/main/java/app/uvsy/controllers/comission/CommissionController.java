package app.uvsy.controllers.comission;

import app.uvsy.controllers.comission.payload.UpdateCommissionPayload;
import app.uvsy.response.Response;
import app.uvsy.service.CommissionService;
import org.github.serverless.api.annotations.HttpMethod;
import org.github.serverless.api.annotations.handler.Handler;
import org.github.serverless.api.annotations.parameters.BodyParameter;
import org.github.serverless.api.annotations.parameters.PathParameter;

public class CommissionController {

    private final CommissionService commissionService;

    public CommissionController() {
        this.commissionService = new CommissionService();
    }

    public CommissionController(CommissionService commissionService) {
        this.commissionService = commissionService;
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/commissions/{id}")
    public Response getCommission(@PathParameter(name = "id") String commissionId) {
        return Response.of(commissionService.getCommission(commissionId));
    }

    @Handler(method = HttpMethod.PUT, resource = "/v1/commissions/{id}")
    public void updateCommission(@PathParameter(name = "id") String commissionId, @BodyParameter UpdateCommissionPayload payload) {
        commissionService.updateCommission(
                commissionId,
                payload.getName(),
                payload.getLevel()
        );
    }

    @Handler(method = HttpMethod.DELETE, resource = "/v1/commissions/{id}")
    public void deleteCommission(@PathParameter(name = "id") String commissionId) {
        commissionService.deleteCommission(commissionId);
    }
}
