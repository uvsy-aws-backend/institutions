package app.uvsy.service;

import app.uvsy.database.DBConnection;
import app.uvsy.database.exceptions.DBException;
import app.uvsy.model.Career;
import app.uvsy.model.Institution;
import app.uvsy.service.exceptions.RecordActiveException;
import app.uvsy.service.exceptions.RecordNotFoundException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class InstitutionService {


    public List<Institution> getAll() {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Institution, String> institutionsDao = DaoManager.createDao(conn, Institution.class);
            return institutionsDao.queryForAll();
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public Institution getInstitution(String institutionId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Institution, String> institutionsDao = DaoManager.createDao(conn, Institution.class);
            return Optional.ofNullable(institutionsDao.queryForId(institutionId))
                    .orElseThrow(() -> new RecordNotFoundException(institutionId));
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void createInstitution(String name, String codename) {

        Institution institution = new Institution();
        institution.setName(name);
        institution.setCodename(codename);
        institution.setActive(Boolean.FALSE);

        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Institution, String> institutionsDao = DaoManager.createDao(conn, Institution.class);
            institutionsDao.create(institution);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void updateInstitution(String institutionId, String codename) {

        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Institution, String> institutionsDao = DaoManager.createDao(conn, Institution.class);
            Institution institution = Optional.ofNullable(institutionsDao.queryForId(institutionId))
                    .orElseThrow(() -> new RecordNotFoundException(institutionId));

            institution.setCodename(codename);
            institutionsDao.update(institution);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void activateInstitution(String institutionId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Institution, String> institutionsDao = DaoManager.createDao(conn, Institution.class);
            Institution institution = institutionsDao.queryForId(institutionId);
            if (!institution.isActive()) {
                institution.activate();
                institutionsDao.update(institution);
            }
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void deleteInstitution(String institutionId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Institution, String> institutionsDao = DaoManager.createDao(conn, Institution.class);
            Institution institution = institutionsDao.queryForId(institutionId);

            if (institution.isActive()) {
                throw new RecordActiveException(institutionId);
            }

            institutionsDao.delete(institution);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void createCareer(String institutionId, String name, String codename) {

        Career career = new Career();
        career.setName(name);
        career.setCodename(codename);
        career.setActive(Boolean.FALSE);
        career.setInstitutionId(institutionId);

        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Career, String> careersDao = DaoManager.createDao(conn, Career.class);
            careersDao.create(career);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public List<Career> getCareers(String institutionId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Career, String> careersDao = DaoManager.createDao(conn, Career.class);
            return careersDao.queryBuilder()
                    .selectColumns()
                    .where()
                    .eq("institution_id", institutionId)
                    .query();
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }
}
