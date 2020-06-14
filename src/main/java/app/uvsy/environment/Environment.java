package app.uvsy.environment;

import app.uvsy.environment.exceptions.CorruptEnvironmentException;

import java.util.Optional;

public class Environment {


    public static String getDBDriver() {
        return Optional.ofNullable(System.getenv("DB_DRIVER"))
                .orElseThrow(() -> new CorruptEnvironmentException("DB_DRIVER"));
    }

    public static String getDBProtocol() {
        return Optional.ofNullable(System.getenv("DB_PROTOCOL"))
                .orElseThrow(() -> new CorruptEnvironmentException("DB_PROTOCOL"));
    }

    public static String getDBPort() {
        return Optional.ofNullable(System.getenv("DB_PORT"))
                .orElseThrow(() -> new CorruptEnvironmentException("DB_PORT"));
    }

    public static String getDBName() {
        return Optional.ofNullable(System.getenv("DB_NAME"))
                .orElseThrow(() -> new CorruptEnvironmentException("DB_NAME"));
    }

    public static String getDBHost() {
        return Optional.ofNullable(System.getenv("DB_HOST"))
                .orElseThrow(() -> new CorruptEnvironmentException("DB_HOST"));
    }

    public static String getDBURL() {
        return String.format(
                "%s:%s://%s:%s/%s",
                getDBDriver(),
                getDBProtocol(),
                getDBHost(),
                getDBPort(),
                getDBName()
        );
    }


    public static String getDBUsername() {
        return Optional.ofNullable(System.getenv("DB_USERNAME"))
                .orElseThrow(() -> new CorruptEnvironmentException("DB_PASSWORD"));
    }

    public static String getDBPassword() {
        return Optional.ofNullable(System.getenv("DB_PASSWORD"))
                .orElseThrow(() -> new CorruptEnvironmentException("DB_PASSWORD"));
    }
}
