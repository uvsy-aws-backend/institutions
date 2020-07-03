package app.uvsy.service;

import app.uvsy.database.DBConnection;
import app.uvsy.database.exceptions.DBException;
import app.uvsy.model.Commission;
import app.uvsy.service.exceptions.RecordNotFoundException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class CommissionService {


    public Commission getCommission(String commissionId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Commission, String> commissionsDao = DaoManager.createDao(conn, Commission.class);
            return Optional.ofNullable(commissionsDao.queryForId(commissionId))
                    .orElseThrow(() -> new RecordNotFoundException(commissionId));
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }

    public void updateCommission(String commissionId, String name, Integer level) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Commission, String> commissionsDao = DaoManager.createDao(conn, Commission.class);
            Commission commission = Optional.ofNullable(commissionsDao.queryForId(commissionId))
                    .orElseThrow(() -> new RecordNotFoundException(commissionId));

            commission.setName(name);
            commission.setLevel(level);

            commissionsDao.update(commission);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }


    public void deleteCommission(String commissionId) {
        try (ConnectionSource conn = DBConnection.create()) {
            Dao<Commission, String> commissionsDao = DaoManager.createDao(conn, Commission.class);
            Commission commission = commissionsDao.queryForId(commissionId);
            commissionsDao.delete(commission);
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
            throw new DBException(e);
        }
    }
}
