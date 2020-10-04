package app.uvsy.queries;

import app.uvsy.apis.exceptions.APIClientException;
import app.uvsy.apis.ratings.RatingsAPI;
import app.uvsy.apis.ratings.model.subject.SubjectRating;
import app.uvsy.apis.ratings.model.subject.SubjectRatingQueryResult;
import app.uvsy.database.DBConnection;
import app.uvsy.database.exceptions.DBException;
import app.uvsy.environment.Environment;
import app.uvsy.model.Program;
import app.uvsy.model.Subject;
import app.uvsy.model.reports.program.ProgramReport;
import app.uvsy.model.reports.program.SubjectStats;
import app.uvsy.queries.exceptions.QueryException;
import app.uvsy.service.exceptions.RecordNotFoundException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProgramReportQuery implements Query<ProgramReport> {

    private final String programId;
    private final RatingsAPI ratingsAPI;

    public ProgramReportQuery(String programId, RatingsAPI ratingsAPI) {
        this.programId = programId;
        this.ratingsAPI = ratingsAPI;
    }

    public ProgramReportQuery(String programId) {
        this(
                programId,
                new RatingsAPI(Environment.getStage())
        );
    }

    @Override
    public ProgramReport execute() {

        // This query provides rating metrics for each subject
        // The order of execution is
        // 1) Fetch all the subjects from the DB
        // 2) For each subject fetch all the programs
        // 2.b) Query api-ratings for getting the program rating
        try (ConnectionSource conn = DBConnection.create()) {
            // Step 1)
            Program program = fetchProgram(conn, programId);

            // Step 2)
            List<Subject> subjects = fetchSubjects(conn, program);

            // Step 2.b)
            Optional<SubjectRatingQueryResult> queryResult = ratingsAPI.postSubjectQuery(
                    subjects.stream()
                            .map(Subject::getId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList()));

            double programRating = queryResult
                    .map(SubjectRatingQueryResult::getRating)
                    .orElse(0.0);

            List<SubjectRating> subjectRatings = queryResult
                    .map(SubjectRatingQueryResult::getSubjectRatings)
                    .orElse(Collections.emptyList());

            List<SubjectStats> subjectStats = getSubjectStats(subjects, subjectRatings);

            return new ProgramReport(program.getId(), program.getName(), programRating, subjectStats);
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

    @NotNull
    private List<SubjectStats> getSubjectStats(List<Subject> subjects, List<SubjectRating> subjectRatings) {
        Map<String, Double> subjectIdRating = subjectRatings.stream()
                .collect(
                        Collectors.toMap(
                                SubjectRating::getSubjectId,
                                SubjectRating::getRating
                        )
                );

        return subjects.stream()
                .map(s -> new SubjectStats(
                        s.getId(),
                        s.getName(),
                        subjectIdRating.getOrDefault(s.getId(), 0.0),
                        s.getOptative()
                ))
                .collect(Collectors.toList());
    }

    private Program fetchProgram(ConnectionSource conn, String programId) throws SQLException {
        Dao<Program, String> programsDao = DaoManager.createDao(conn, Program.class);
        return Optional.ofNullable(programsDao.queryForId(programId))
                .orElseThrow(() -> new RecordNotFoundException(programId));
    }

    private List<Subject> fetchSubjects(ConnectionSource conn, Program program) throws SQLException {
        return DaoManager.createDao(conn, Subject.class).queryBuilder()
                .selectColumns()
                .where()
                .eq("program_id", program.getId())
                .query();
    }
}
