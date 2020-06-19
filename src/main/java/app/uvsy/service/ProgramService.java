package app.uvsy.service;

import app.uvsy.database.DBConnection;
import app.uvsy.database.DBException;
import app.uvsy.model.Program;
import app.uvsy.model.Subject;
import app.uvsy.service.exceptions.RecordActiveException;
import app.uvsy.service.exceptions.RecordNotFoundException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    public void updateProgram(String programId, String codename) {

        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Program, String> programsDao = DaoManager.createDao(conn, Program.class);
            Program program = Optional.ofNullable(programsDao.queryForId(programId))
                    .orElseThrow(() -> new RecordNotFoundException(programId));

            programsDao.update(program);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
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

    public void createSubject(String programId, String name, Integer hours, Integer points) {

        Subject subject = new Subject();
        subject.setName(name);
        subject.setActive(Boolean.FALSE);
        subject.setHours(hours);
        subject.setPoints(points);
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
            Dao<Subject, String> subjectDao = DaoManager.createDao(conn, Subject.class);
            return subjectDao.queryBuilder()
                    .selectColumns()
                    .where()
                    .eq("program_id", programId)
                    .query();
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }
}