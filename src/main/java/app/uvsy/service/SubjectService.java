package app.uvsy.service;

import app.uvsy.database.DBConnection;
import app.uvsy.database.DBException;
import app.uvsy.model.Subject;
import app.uvsy.service.exceptions.RecordActiveException;
import app.uvsy.service.exceptions.RecordNotFoundException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class SubjectService {


    public Subject getSubject(String subjectId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Subject, String> subjectsDao = DaoManager.createDao(conn, Subject.class);
            return Optional.ofNullable(subjectsDao.queryForId(subjectId))
                    .orElseThrow(() -> new RecordNotFoundException(subjectId));
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void updateSubject(String subjectId, String name, String codename, Integer hours, Integer points, Boolean optative) {

        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Subject, String> subjectsDao = DaoManager.createDao(conn, Subject.class);
            Subject subject = Optional.ofNullable(subjectsDao.queryForId(subjectId))
                    .orElseThrow(() -> new RecordNotFoundException(subjectId));

            subject.setName(name);
            subject.setCodename(codename);
            subject.setHours(hours);
            subject.setPoints(points);
            subject.setOptative(optative);

            subjectsDao.update(subject);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }


    public void activateSubject(String subjectId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Subject, String> subjectsDao = DaoManager.createDao(conn, Subject.class);
            Subject subject = subjectsDao.queryForId(subjectId);
            if (!subject.isActive()) {
                subject.activate();
                subjectsDao.update(subject);
            }
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void deleteSubject(String subjectId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Subject, String> subjectsDao = DaoManager.createDao(conn, Subject.class);
            Subject subject = subjectsDao.queryForId(subjectId);

            if (subject.isActive()) {
                throw new RecordActiveException(subjectId);
            }

            subjectsDao.delete(subject);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }
}
