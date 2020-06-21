package app.uvsy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Optional;


@Getter
@Setter
@DatabaseTable(tableName = "subject")
public class Subject {

    @DatabaseField(columnName = "id", id = true, readOnly = true)
    private String id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "codename")
    private String codename;

    @DatabaseField(columnName = "program_id")
    private String programId;

    @JsonIgnore
    @DatabaseField(foreign = true, readOnly = true)
    private Program program;

    @DatabaseField(columnName = "hours")
    private Integer hours;

    @DatabaseField(columnName = "points")
    private Integer points;

    @DatabaseField(columnName = "active")
    private Boolean active;

    @DatabaseField(columnName = "optative")
    private Boolean optative;

    @DatabaseField(columnName = "created_at", readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(columnName = "updated_at", readOnly = true)
    private Timestamp updatedAt;

    public boolean isActive() {
        return Optional.ofNullable(active).orElse(Boolean.FALSE);
    }

    public void activate() {
        this.active = Boolean.TRUE;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", codename='" + codename + '\'' +
                ", programId='" + programId + '\'' +
                ", hours=" + hours +
                ", points=" + points +
                ", active=" + active +
                ", optative=" + optative +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
