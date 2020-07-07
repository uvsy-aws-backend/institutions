package app.uvsy.service;

import app.uvsy.database.DBConnection;
import app.uvsy.database.exceptions.DBException;
import app.uvsy.model.Career;
import app.uvsy.model.Program;
import app.uvsy.service.exceptions.RecordActiveException;
import app.uvsy.service.exceptions.RecordNotFoundException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
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

    public void updateCareer(String careerId, String name, String codename) {

        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Career, String> careersDao = DaoManager.createDao(conn, Career.class);
            Career career = Optional.ofNullable(careersDao.queryForId(careerId))
                    .orElseThrow(() -> new RecordNotFoundException(careerId));

            career.setName(name);
            career.setCodename(codename);
            careersDao.update(career);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }


    public void activateCareer(String careerId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Career, String> careersDao = DaoManager.createDao(conn, Career.class);
            Career career = careersDao.queryForId(careerId);
            if (!career.isActive()) {
                career.activate();
                careersDao.update(career);
            }
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

    public List<Program> getPrograms(String careerId) {
        try (ConnectionSource conn = DBConnection.create()) {

            Dao<Career, String> careerDao = DaoManager.createDao(conn, Career.class);
            boolean careerExists = Optional.ofNullable(careerDao.queryForId(careerId)).isPresent();

            if (careerExists) {
                Dao<Program, String> programDao = DaoManager.createDao(conn, Program.class);
                return programDao.queryBuilder()
                        .selectColumns()
                        .where()
                        .eq("career_id", careerId)
                        .query();
            }
            throw new RecordNotFoundException(careerId);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void createProgram(String careerId, String name, Date validFrom, Date validTo, Integer hours, Integer points, Integer amountOfSubjects) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Career, String> careerDao = DaoManager.createDao(conn, Career.class);
            boolean careerExists = Optional.ofNullable(careerDao.queryForId(careerId)).isPresent();

            if (careerExists) {

                Program program = new Program();
                program.setName(name);
                program.setActive(Boolean.FALSE);
                program.setCareerId(careerId);
                program.setValidFrom(validFrom);
                program.setValidTo(validTo);
                program.setHours(hours);
                program.setPoints(points);
                program.setAmountOfSubjects(amountOfSubjects);

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
}
