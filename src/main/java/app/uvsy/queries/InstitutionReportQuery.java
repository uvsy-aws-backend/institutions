package app.uvsy.queries;

import app.uvsy.apis.exceptions.APIClientException;
import app.uvsy.apis.ratings.RatingsAPI;
import app.uvsy.apis.ratings.model.SubjectRatingQueryResult;
import app.uvsy.database.DBConnection;
import app.uvsy.database.exceptions.DBException;
import app.uvsy.environment.Environment;
import app.uvsy.model.Career;
import app.uvsy.model.Program;
import app.uvsy.model.Subject;
import app.uvsy.model.reports.institution.CareerStats;
import app.uvsy.model.reports.institution.InstitutionReport;
import app.uvsy.model.reports.institution.ProgramStats;
import app.uvsy.queries.exceptions.QueryException;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InstitutionReportQuery implements Query<InstitutionReport> {

    private final String institutionId;
    private final RatingsAPI ratingsAPI;

    public InstitutionReportQuery(String institutionId, RatingsAPI ratingsAPI) {
        this.institutionId = institutionId;
        this.ratingsAPI = ratingsAPI;
    }

    public InstitutionReportQuery(String institutionId) {
        this(
                institutionId,
                new RatingsAPI(Environment.getStage())
        );
    }

    @Override
    public InstitutionReport execute() {

        // This query provides rating metrics for each career
        // The order of execution is
        // 1) Fetch all the careers from the DB
        // 2) For each career fetch all the programs
        // 2.a) For each programs fetch all the subjects
        // 2.b) Query api-ratings for getting the program rating
        try (ConnectionSource conn = DBConnection.create()) {
            // Step 1)
            List<Career> careers = fetchCareers(conn);

            // Step 2)
            List<CareerStats> careerStats = getCareersStats(conn, careers);
            double institutionRating = careerStats
                    .stream()
                    .mapToDouble(CareerStats::getRating)
                    .average()
                    .orElse(0.0);
            return new InstitutionReport(institutionRating, careerStats);
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

    private List<CareerStats> getCareersStats(ConnectionSource conn, List<Career> careers) throws SQLException, APIClientException {
        List<CareerStats> careerStatsList = new ArrayList<>();
        for (Career c : careers) {
            CareerStats careerStats = new CareerStats();
            careerStats.setCareerId(c.getId());
            careerStats.setCareerName(c.getName());

            List<Program> programs = fetchPrograms(conn, c);
            List<ProgramStats> programStatsList = getProgramsStats(conn, programs);

            careerStats.setPrograms(programStatsList);
            careerStatsList.add(careerStats);
        }
        return careerStatsList;
    }

    private List<ProgramStats> getProgramsStats(ConnectionSource conn, List<Program> programs) throws SQLException, APIClientException {
        List<ProgramStats> programStatsList = new ArrayList<>();
        for (Program p : programs) {
            ProgramStats programStats = new ProgramStats();
            programStats.setProgramId(p.getId());
            programStats.setProgramName(p.getName());

            List<Subject> subjects = fetchSubjects(conn, p);

            double rating = ratingsAPI.postSubjectQuery(subjects
                    .stream()
                    .map(Subject::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()))
                    .map(SubjectRatingQueryResult::getRating)
                    .orElse(0.0);

            int amountOfOptatives = Math.toIntExact(subjects.stream()
                    .filter(Subject::getOptative)
                    .count());

            int amountOfSubjects = subjects.size();

            programStats.setRating(rating);
            programStats.setAmountOfOptatives(amountOfOptatives);
            programStats.setAmountOfSubjects(amountOfSubjects);
            programStatsList.add(programStats);
        }
        return programStatsList;
    }

    private List<Career> fetchCareers(ConnectionSource conn) throws SQLException {
        return DaoManager.createDao(conn, Career.class)
                .queryBuilder()
                .selectColumns()
                .where()
                .eq("institution_id", institutionId)
                .query();
    }

    private List<Program> fetchPrograms(ConnectionSource conn, Career career) throws SQLException {
        return DaoManager.createDao(conn, Program.class)
                .queryBuilder()
                .selectColumns()
                .where()
                .eq("career_id", career.getId())
                .query();
    }

    private List<Subject> fetchSubjects(ConnectionSource conn, Program program) throws SQLException {
        return DaoManager.createDao(conn, Subject.class).queryBuilder()
                .selectColumns()
                .where()
                .eq("program_id", program.getId())
                .query();
    }
}
