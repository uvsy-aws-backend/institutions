package app.uvsy.controllers.program;

import app.uvsy.controllers.program.payload.CreateCommissionPayload;
import app.uvsy.controllers.program.payload.CreateSubjectPayload;
import app.uvsy.controllers.program.payload.UpdateProgramPayload;
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

    @Handler(method = HttpMethod.GET, resource = "/programs/{id}")
    public Response getProgram(@PathParameter(name = "id") String programId) {
        return Response.of(programService.getProgram(programId));
    }

    @Handler(method = HttpMethod.PUT, resource = "/programs/{id}")
    public void updateProgram(@PathParameter(name = "id") String programId, @BodyParameter UpdateProgramPayload payload) {
        programService.updateProgram(
                programId,
                payload.getName(),
                payload.getValidFrom(),
                payload.getValidTo(),
                payload.getHours(),
                payload.getPoints()
        );
    }

    @Handler(method = HttpMethod.DELETE, resource = "/programs/{id}")
    public void deleteProgram(@PathParameter(name = "id") String programId) {
        programService.deleteProgram(programId);
    }

    @Handler(method = HttpMethod.POST, resource = "/programs/{id}/activate")
    public void activateProgram(@PathParameter(name = "id") String programId) {
        programService.activateProgram(programId);
    }

    @Handler(method = HttpMethod.GET, resource = "/programs/{id}/subjects")
    public Response getSubjects(@PathParameter(name = "id") String programId) {
        return Response.of(programService.getSubjects(programId));
    }

    @Handler(method = HttpMethod.POST, resource = "/programs/{id}/subjects")
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

    @Handler(method = HttpMethod.GET, resource = "/programs/{id}/commissions")
    public Response getCommissions(@PathParameter(name = "id") String programId) {
        return Response.of(programService.getCommission(programId));
    }

    @Handler(method = HttpMethod.POST, resource = "/programs/{id}/commissions")
    public void createCommission(@PathParameter(name = "id") String programId, @BodyParameter CreateCommissionPayload payload) {
        programService.createCommission(
                programId,
                payload.getName(),
                payload.getLevel()
        );
    }
}
