package app.uvsy.queries;

import app.uvsy.apis.exceptions.APIClientException;
import app.uvsy.apis.ratings.RatingsAPI;
import app.uvsy.apis.ratings.model.course.CourseRating;
import app.uvsy.apis.ratings.model.course.CourseRatingQueryResult;
import app.uvsy.apis.ratings.model.subject.SubjectRatingQueryResult;
import app.uvsy.database.DBConnection;
import app.uvsy.database.DynamoDBDAO;
import app.uvsy.database.DynamoDBIndexes;
import app.uvsy.database.exceptions.DBException;
import app.uvsy.environment.Environment;
import app.uvsy.model.Commission;
import app.uvsy.model.Course;
import app.uvsy.model.Subject;
import app.uvsy.model.reports.subject.CourseStats;
import app.uvsy.model.reports.subject.SubjectReport;
import app.uvsy.queries.exceptions.QueryException;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SubjectReportQuery implements Query<SubjectReport> {

    private final String subjectId;
    private final RatingsAPI ratingsAPI;

    public SubjectReportQuery(String subjectId, RatingsAPI ratingsAPI) {
        this.subjectId = subjectId;
        this.ratingsAPI = ratingsAPI;
    }

    public SubjectReportQuery(String subjectId) {
        this(
                subjectId,
                new RatingsAPI(Environment.getStage())
        );
    }

    @Override
    public SubjectReport execute() {

        // This query provides rating metrics for each career
        // The order of execution is
        // 1) Fetch all the courses from the DB
        // 2) For each course fetch the commission
        // 3) Fetch stats for courses
        try (ConnectionSource conn = DBConnection.create()) {
            // Step 1)
            List<Course> courses = fetchCourses();

            // Step 2)
            Map<String, String> commissionNameMap = fetchCommissionsName(conn, courses);

            // Step 3)
            List<CourseStats> courseStats = getCoursesStats(courses, commissionNameMap);
            return new SubjectReport(courseStats);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        } catch (APIClientException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new QueryException(e);
        }
    }


    private List<CourseStats> getCoursesStats(List<Course> courses,
                                              Map<String, String> commissionMap) throws APIClientException {

        Map<String, CourseRating> courseRatingMap = ratingsAPI.postCoursesQuery(courses
                .stream()
                .map(Course::getCourseId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()))
                .map(CourseRatingQueryResult::getCourseRatings)
                .orElse(Collections.emptyList())
                .stream()
                .collect(
                        Collectors.toMap(
                                CourseRating::getCourseId,
                                Function.identity()
                        )
                );

        return courses.stream()
                .map(c -> createCourseStats(c, commissionMap, courseRatingMap))
                .collect(Collectors.toList());
    }

    private CourseStats createCourseStats(Course c,
                                          Map<String, String> commissionMap,
                                          Map<String, CourseRating> courseRatingMap) {
        CourseStats courseStats = new CourseStats();
        courseStats.setCourseId(c.getCourseId());
        courseStats.setCommissionName(
                commissionMap.getOrDefault(c.getCommissionId(), "")
        );

        Optional<CourseRating> courseRating = Optional.of(c.getCourseId())
                .map(courseRatingMap::get);

        double rating = courseRating.map(CourseRating::getOverallRating).orElse(0.0);
        courseStats.setRating(rating);

        double difficulty = courseRating.map(CourseRating::getDifficultyRating).orElse(0.0);
        courseStats.setDifficulty(difficulty);


        double wouldTakeAgain = courseRating.map(CourseRating::getWouldTakeAgainRating).orElse(0.0);
        courseStats.setWouldTakeAgain(wouldTakeAgain);
        return courseStats;
    }

    private List<Course> fetchCourses() {
        Course course = new Course();
        course.setSubjectId(subjectId);

        DynamoDBDAO<Course> courseDAO = DynamoDBDAO.createFor(Course.class);
        return courseDAO.query(course, DynamoDBIndexes.SUBJECT_ID_INDEX);
    }

    private Map<String, String> fetchCommissionsName(ConnectionSource conn, List<Course> courses) throws SQLException {
        List<String> ids = courses.stream().map(Course::getCommissionId).collect(Collectors.toList());
        return ids.isEmpty()
                ? Collections.emptyMap()
                : DaoManager.createDao(conn, Commission.class)
                .queryBuilder()
                .selectColumns()
                .where()
                .in("id", ids)
                .query()
                .stream()
                .collect(
                        Collectors.toMap(
                                Commission::getId,
                                Commission::getName
                        )
                );
    }

}
