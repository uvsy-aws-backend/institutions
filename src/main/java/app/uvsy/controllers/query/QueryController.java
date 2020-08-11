package app.uvsy.controllers.query;

import app.uvsy.response.Response;
import app.uvsy.service.QueryService;
import org.github.serverless.api.annotations.HttpMethod;
import org.github.serverless.api.annotations.handler.Handler;
import org.github.serverless.api.annotations.parameters.QueryParameter;

import java.util.List;

public class QueryController {

    private final QueryService queryService;

    public QueryController() {
        this.queryService = new QueryService();
    }

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/query/programs/info")
    public Response getProgramInfo(@QueryParameter(name = "programIds") List<String> programIds) {
        return Response.of(queryService.queryProgramsInfo(programIds));
    }
}
