package app.uvsy.controllers.program;

import app.uvsy.controllers.program.payload.CreateCommissionPayload;
import app.uvsy.controllers.program.payload.CreateSubjectPayload;
import app.uvsy.controllers.program.payload.UpdateProgramPayload;
import app.uvsy.model.reports.program.ProgramReport;
import app.uvsy.response.Response;
import app.uvsy.service.ProgramService;
import org.github.serverless.api.annotations.HttpMethod;
import org.github.serverless.api.annotations.handler.Handler;
import org.github.serverless.api.annotations.parameters.BodyParameter;
import org.github.serverless.api.annotations.parameters.PathParameter;

public class ProgramController {

    private final ProgramService programService;

    public ProgramController() {
        this.programService = new ProgramService();
    }

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/programs/{id}")
    public Response getProgram(@PathParameter(name = "id") String programId) {
        return Response.of(programService.getProgram(programId));
    }

    @Handler(method = HttpMethod.PUT, resource = "/v1/programs/{id}")
    public void updateProgram(@PathParameter(name = "id") String programId, @BodyParameter UpdateProgramPayload payload) {
        programService.updateProgram(
                programId,
                payload.getName(),
                payload.getActive(),
                payload.getYearFrom(),
                payload.getYearTo(),
                payload.getHours(),
                payload.getPoints(),
                payload.getAmountOfSubjects()
        );
    }

    @Handler(method = HttpMethod.DELETE, resource = "/v1/programs/{id}")
    public void deleteProgram(@PathParameter(name = "id") String programId) {
        programService.deleteProgram(programId);
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/programs/{id}/subjects")
    public Response getSubjects(@PathParameter(name = "id") String programId) {
        return Response.of(programService.getSubjects(programId));
    }

    @Handler(method = HttpMethod.POST, resource = "/v1/programs/{id}/subjects")
    public void createSubject(@PathParameter(name = "id") String programId, @BodyParameter CreateSubjectPayload payload) {
        programService.createSubject(
                programId,
                payload.getName(),
                payload.getCodename(),
                payload.getLevel(),
                payload.getHours(),
                payload.getPoints(),
                payload.getOptative()
        );
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/programs/{id}/commissions")
    public Response getCommissions(@PathParameter(name = "id") String programId) {
        return Response.of(programService.getCommission(programId));
    }

    @Handler(method = HttpMethod.POST, resource = "/v1/programs/{id}/commissions")
    public void createCommission(@PathParameter(name = "id") String programId, @BodyParameter CreateCommissionPayload payload) {
        programService.createCommission(
                programId,
                payload.getName(),
                payload.getLevel()
        );
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/programs/{id}/report")
    public Response getProgramReport(@PathParameter(name = "id") String programId) {
        return Response.of(programService.getProgramReport(programId));
    }
}
