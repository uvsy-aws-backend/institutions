package app.uvsy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;


@Data
@DatabaseTable(tableName = "subject")
public class Subject {

    @DatabaseField(columnName = "id", id = true, readOnly = true)
    private String id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "codename")
    private String codename;

    @DatabaseField(columnName = "level")
    private Integer level;

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

    @ForeignCollectionField(eager = true, foreignFieldName = "subject")
    private Collection<Correlative> correlatives;

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
}
