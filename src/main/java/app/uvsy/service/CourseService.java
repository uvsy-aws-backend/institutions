package app.uvsy.service;

import app.uvsy.database.DynamoDBDAO;
import app.uvsy.model.Course;
import app.uvsy.model.course.CoursingPeriod;
import app.uvsy.service.exceptions.RecordNotFoundException;

import java.util.List;

public class CourseService {


    public Course getCourse(String courseId) {
        DynamoDBDAO<Course> courseDAO = DynamoDBDAO.createFor(Course.class);
        return courseDAO.get(courseId)
                .orElseThrow(() -> new RecordNotFoundException(courseId));
    }

    public void updateCourse(String courseId, List<CoursingPeriod> periods, boolean active) {
        DynamoDBDAO<Course> courseDAO = DynamoDBDAO.createFor(Course.class);

        Course course = courseDAO.get(courseId)
                .orElseThrow(() -> new RecordNotFoundException(courseId));

        course.setPeriods(periods);
        course.setActive(active);
        courseDAO.save(course);

    }

    public void deleteCourse(String courseId) {
        DynamoDBDAO<Course> courseDAO = DynamoDBDAO.createFor(Course.class);
        courseDAO.get(courseId).ifPresent(courseDAO::delete);
    }
}
