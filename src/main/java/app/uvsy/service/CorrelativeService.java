package app.uvsy.service;

import app.uvsy.database.DBConnection;
import app.uvsy.database.DBException;
import app.uvsy.model.Correlative;
import app.uvsy.model.CorrelativeCondition;
import app.uvsy.model.CorrelativeRestriction;
import app.uvsy.service.exceptions.RecordNotFoundException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CorrelativeService {


    public Correlative getCorrelative(String correlativeId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Correlative, String> correlativesDao = DaoManager.createDao(conn, Correlative.class);
            return Optional.ofNullable(correlativesDao.queryForId(correlativeId))
                    .orElseThrow(() -> new RecordNotFoundException(correlativeId));
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void updateCorrelative(String correlativeId, String correlativeSubjectId, CorrelativeRestriction restriction, CorrelativeCondition condition) {

        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Correlative, String> correlativesDao = DaoManager.createDao(conn, Correlative.class);
            Correlative correlative = Optional.ofNullable(correlativesDao.queryForId(correlativeId))
                    .orElseThrow(() -> new RecordNotFoundException(correlativeId));

            correlative.setCorrelativeSubjectId(correlativeSubjectId);
            correlative.setCorrelativeCondition(condition);
            correlative.setCorrelativeRestriction(restriction);

            correlativesDao.update(correlative);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }


    public void deleteCorrelative(String correlativeId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Correlative, String> correlativesDao = DaoManager.createDao(conn, Correlative.class);
            Correlative correlative = correlativesDao.queryForId(correlativeId);
            correlativesDao.delete(correlative);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

}
