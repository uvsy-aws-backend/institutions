package app.uvsy.service;

import app.uvsy.database.DBConnection;
import app.uvsy.database.exceptions.DBException;
import app.uvsy.model.Commission;
import app.uvsy.model.Program;
import app.uvsy.model.Subject;
import app.uvsy.service.exceptions.RecordActiveException;
import app.uvsy.service.exceptions.RecordConflictException;
import app.uvsy.service.exceptions.RecordNotFoundException;
import app.uvsy.service.filters.program.ProgramOverlapFilter;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class ProgramService {

    public Program getProgram(String programId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Program, String> programsDao = DaoManager.createDao(conn, Program.class);
            return Optional.ofNullable(programsDao.queryForId(programId))
                    .orElseThrow(() -> new RecordNotFoundException(programId));
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void updateProgram(String programId, String name, Integer yearFrom, Integer yearTo,
                              Integer hours, Integer points, Integer amountOfSubjects) {
        try (ConnectionSource conn = DBConnection.create()) {

            Dao<Program, String> programsDao = DaoManager.createDao(conn, Program.class);

            Program program = Optional.ofNullable(programsDao.queryForId(programId))
                    .orElseThrow(() -> new RecordNotFoundException(programId));

            // TODO: Check that yearfrom < yearTo with a proper exception.
            if (validUpdate(program, name, yearFrom, hours, points)) {
                if (validDateRange(programsDao, program)) {
                    program.setName(name);
                    program.setYearFrom(yearFrom);
                    program.setYearTo(yearTo);
                    program.setHours(hours);
                    program.setPoints(points);
                    program.setAmountOfSubjects(amountOfSubjects);
                    programsDao.update(program);
                } else {
                    throw new RecordConflictException();
                }
            } else {
                throw new RecordActiveException(programId);
            }
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    private boolean validUpdate(Program program, String name, Integer yearFrom, Integer hours, Integer points) {
        return !program.isActive()
                || (equal(program::getName, name)
                && equal(program::getYearFrom, yearFrom)
                && equal(program::getHours, hours)
                && equal(program::getPoints, points));

    }

    private boolean validDateRange(Dao<Program, String> programsDao, Program program) throws SQLException {
        List<Program> programs = programsDao.queryBuilder()
                .selectColumns()
                .where()
                .eq("career_id", program.getCareerId())
                .query();

        ProgramOverlapFilter overlapFilter = new ProgramOverlapFilter(program);
        return programs.stream().noneMatch(overlapFilter::apply);
    }

    private <T> boolean equal(Supplier<T> getter, T value) {
        return getter.get() != null && getter.get().equals(value);
    }

    public void activateProgram(String programId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Program, String> programsDao = DaoManager.createDao(conn, Program.class);
            Program program = programsDao.queryForId(programId);
            if (!program.isActive()) {
                program.activate();
                programsDao.update(program);
            }
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void deleteProgram(String programId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Program, String> programsDao = DaoManager.createDao(conn, Program.class);
            Program program = programsDao.queryForId(programId);

            if (program.isActive()) {
                throw new RecordActiveException(programId);
            }

            programsDao.delete(program);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void createSubject(String programId, String name, String codename, Integer level, Integer hours, Integer points, Boolean optative) {

        Subject subject = new Subject();
        subject.setName(name);
        subject.setCodename(codename);
        subject.setLevel(level);
        subject.setActive(Boolean.FALSE);
        subject.setHours(hours);
        subject.setPoints(points);
        subject.setOptative(optative);
        subject.setProgramId(programId);


        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Subject, String> subjectDao = DaoManager.createDao(conn, Subject.class);
            subjectDao.create(subject);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public List<Subject> getSubjects(String programId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Program, String> programDao = DaoManager.createDao(conn, Program.class);
            boolean programExists = Optional.ofNullable(programDao.queryForId(programId)).isPresent();

            if (programExists) {
                Dao<Subject, String> subjectDao = DaoManager.createDao(conn, Subject.class);
                return subjectDao.queryBuilder()
                        .selectColumns()
                        .where()
                        .eq("program_id", programId)
                        .query();
            }
            throw new RecordNotFoundException(programId);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public List<Commission> getCommission(String programId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Program, String> programDao = DaoManager.createDao(conn, Program.class);
            boolean programExists = Optional.ofNullable(programDao.queryForId(programId)).isPresent();

            if (programExists) {
                Dao<Commission, String> commissionDao = DaoManager.createDao(conn, Commission.class);
                return commissionDao.queryBuilder()
                        .selectColumns()
                        .where()
                        .eq("program_id", programId)
                        .query();
            }
            throw new RecordNotFoundException(programId);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }

    }

    public void createCommission(String programId, String name, Integer level) {

        try (ConnectionSource conn = DBConnection.create()) {

            Dao<Program, String> programDao = DaoManager.createDao(conn, Program.class);
            boolean programExists = Optional.ofNullable(programDao.queryForId(programId)).isPresent();

            if (programExists) {

                Commission commission = new Commission();
                commission.setName(name);
                commission.setLevel(level);
                commission.setProgramId(programId);
                commission.setActive(Boolean.TRUE);

                Dao<Commission, String> commissionDao = DaoManager.createDao(conn, Commission.class);
                commissionDao.create(commission);
            } else {
                throw new RecordNotFoundException(programId);
            }
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }

    }
}
