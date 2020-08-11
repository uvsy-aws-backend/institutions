package app.uvsy.service;

import app.uvsy.database.DBConnection;
import app.uvsy.model.Career;
import app.uvsy.model.Institution;
import app.uvsy.model.Program;
import app.uvsy.model.query.ProgramInfo;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class QueryService {

    public List<ProgramInfo> queryProgramsInfo(List<String> programIds) {
        return programIds.stream()
                .map(this::getProgramInfo)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private Optional<ProgramInfo> getProgramInfo(String programId) {
        try (ConnectionSource conn = DBConnection.create()) {
            return queryForId(conn, programId, Program.class)
                    .flatMap(p -> createProgramInfo(conn, p));
        } catch (SQLException | IOException e) {
            // TODO: Add logger error
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<ProgramInfo> createProgramInfo(ConnectionSource conn, Program program) {
        try {
            ProgramInfo programInfo = new ProgramInfo(program.getId());
            Optional<Career> careerResult = queryForId(conn, program.getCareerId(), Career.class);
            if (careerResult.isPresent()) {
                Career career = careerResult.get();
                programInfo.setCareer(career);

                queryForId(conn, career.getInstitutionId(), Institution.class)
                        .ifPresent(programInfo::setInstitution);
            }
            return Optional.of(programInfo);
        } catch (SQLException e) {
            // TODO: Add logger error
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private <T> Optional<T> queryForId(ConnectionSource conn, String id, Class<T> clazz) throws SQLException {
        Dao<T, String> dao = DaoManager.createDao(conn, clazz);
        return Optional.ofNullable(dao.queryForId(id));
    }
}
