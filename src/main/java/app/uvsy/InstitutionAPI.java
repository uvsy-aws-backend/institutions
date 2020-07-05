package app.uvsy;

import app.uvsy.controllers.career.CareerController;
import app.uvsy.controllers.comission.CommissionController;
import app.uvsy.controllers.correlatives.CorrelativeController;
import app.uvsy.controllers.course.CourseController;
import app.uvsy.controllers.institution.InstitutionController;
import app.uvsy.controllers.program.ProgramController;
import app.uvsy.controllers.subject.SubjectController;
import org.github.serverless.api.ServerlessApiHandler;

import java.util.List;

public class InstitutionAPI extends ServerlessApiHandler {

    @Override
    protected void registerControllers(List<Object> controllers) {
        controllers.add(new InstitutionController());
        controllers.add(new CareerController());
        controllers.add(new ProgramController());
        controllers.add(new SubjectController());
        controllers.add(new CorrelativeController());
        controllers.add(new CommissionController());
        controllers.add(new CourseController());
    }
}
