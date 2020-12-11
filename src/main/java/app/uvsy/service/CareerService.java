package app.uvsy.service;

import app.uvsy.database.DBConnection;
import app.uvsy.database.exceptions.DBException;
import app.uvsy.model.Career;
import app.uvsy.model.Program;
import app.uvsy.service.exceptions.RecordActiveException;
import app.uvsy.service.exceptions.RecordConflictException;
import app.uvsy.service.exceptions.RecordNotFoundException;
import app.uvsy.service.filters.program.ProgramOverlapFilter;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class CareerService {

    public Career getCareer(String careerId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Career, String> careersDao = DaoManager.createDao(conn, Career.class);
            return Optional.ofNullable(careersDao.queryForId(careerId))
                    .orElseThrow(() -> new RecordNotFoundException(careerId));
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void updateCareer(String careerId, String name, String codename, Boolean active) {

        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Career, String> careersDao = DaoManager.createDao(conn, Career.class);
            Career career = Optional.ofNullable(careersDao.queryForId(careerId))
                    .orElseThrow(() -> new RecordNotFoundException(careerId));

            career.setName(name);
            career.setCodename(codename);
            career.setActive(active);
            careersDao.update(career);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void deleteCareer(String careerId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Career, String> careersDao = DaoManager.createDao(conn, Career.class);
            Career career = careersDao.queryForId(careerId);

            if (career.isActive()) {
                throw new RecordActiveException(careerId);
            }

            careersDao.delete(career);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public List<Program> getPrograms(String careerId, Boolean onlyActive) {
        try (ConnectionSource conn = DBConnection.create()) {

            Dao<Career, String> careerDao = DaoManager.createDao(conn, Career.class);
            boolean careerExists = Optional.ofNullable(careerDao.queryForId(careerId)).isPresent();

            if (careerExists) {
                Dao<Program, String> programDao = DaoManager.createDao(conn, Program.class);
                Where<Program, String> queryBuilder = programDao.queryBuilder()
                        .selectColumns()
                        .where()
                        .eq("career_id", careerId);

                if (onlyActive) {
                    queryBuilder = queryBuilder.and().eq("active", true);
                }

                return queryBuilder.query();
            }
            throw new RecordNotFoundException(careerId);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public List<Program> getProgramsForYear(String careerId, Boolean onlyActive, Integer year) {
        try (ConnectionSource conn = DBConnection.create()) {

            Dao<Career, String> careerDao = DaoManager.createDao(conn, Career.class);
            boolean careerExists = Optional.ofNullable(careerDao.queryForId(careerId)).isPresent();

            if (careerExists) {
                Dao<Program, String> programDao = DaoManager.createDao(conn, Program.class);
                Where<Program, String> where = programDao.queryBuilder()
                        .selectColumns()
                        .where();

                //noinspection unchecked
                where = where.and(
                        where.eq("career_id", careerId),
                        where.eq("south_hemisphere", Boolean.TRUE),
                        where.le("year_from", year),
                        where.or(where.ge("year_to", year), where.isNull("year_to"))
                );

                if (onlyActive) {
                    where = where.and()
                            .eq("active", true);
                }
                return where.query();
            }
            throw new RecordNotFoundException(careerId);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void createProgram(String careerId, String name, Integer yearFrom, Integer yearTo,
                              Integer hours, Integer points, Integer amountOfSubjects) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Career, String> careerDao = DaoManager.createDao(conn, Career.class);
            boolean careerExists = Optional.ofNullable(careerDao.queryForId(careerId)).isPresent();

            if (careerExists) {

                Program program = new Program();
                program.setName(name);
                program.setActive(Boolean.FALSE);
                program.setCareerId(careerId);
                program.setYearFrom(yearFrom);
                program.setYearTo(yearTo);
                program.setSouthHemisphere(Boolean.TRUE); // The hemisphere is hardcoded.
                program.setHours(hours);
                program.setPoints(points);
                program.setAmountOfSubjects(amountOfSubjects);

                checkOverlaps(careerId, program);

                Dao<Program, String> programsDao = DaoManager.createDao(conn, Program.class);
                programsDao.create(program);
            } else {
                throw new RecordNotFoundException(careerId);
            }
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    private void checkOverlaps(String careerId, Program program) {
        ProgramOverlapFilter overlapFilter = new ProgramOverlapFilter(program);

        if (this.getPrograms(careerId, false)
                .stream()
                .anyMatch(overlapFilter::apply)) {
            throw new RecordConflictException();
        }
    }
}
