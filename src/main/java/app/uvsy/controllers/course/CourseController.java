package app.uvsy.controllers.course;

import app.uvsy.controllers.course.payload.UpdateCoursePayload;
import app.uvsy.response.Response;
import app.uvsy.service.CourseService;
import org.github.serverless.api.annotations.HttpMethod;
import org.github.serverless.api.annotations.handler.Handler;
import org.github.serverless.api.annotations.parameters.BodyParameter;
import org.github.serverless.api.annotations.parameters.PathParameter;

public class CourseController {

    private final CourseService courseService;

    public CourseController() {
        this.courseService = new CourseService();
    }

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @Handler(method = HttpMethod.GET, resource = "/v1/courses/{id}")
    public Response getCourse(@PathParameter(name = "id") String courseId) {
        return Response.of(courseService.getCourse(courseId));
    }

    @Handler(method = HttpMethod.PUT, resource = "/v1/courses/{id}")
    public void updateCourse(@PathParameter(name = "id") String courseId, @BodyParameter UpdateCoursePayload payload) {
        courseService.updateCourse(
                courseId,
                payload.getPeriods(),
                payload.getActive()
        );
    }

    @Handler(method = HttpMethod.DELETE, resource = "/v1/courses/{id}")
    public void deleteCourse(@PathParameter(name = "id") String courseId) {
        courseService.deleteCourse(courseId);
    }
}
