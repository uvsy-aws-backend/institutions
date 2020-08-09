package app.uvsy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Optional;

@Data
@DatabaseTable(tableName = "program")
public class Program {
    @DatabaseField(columnName = "id", id = true, readOnly = true)
    private String id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "career_id")
    private String careerId;

    @JsonIgnore
    @DatabaseField(foreign = true, readOnly = true)
    private Career career;

    @DatabaseField(columnName = "year_from")
    private Integer yearFrom;

    @DatabaseField(columnName = "year_to")
    private Integer yearTo;

    @JsonIgnore
    @DatabaseField(columnName = "south_hemisphere")
    private Boolean southHemisphere;

    @DatabaseField(columnName = "hours")
    private Integer hours;

    @DatabaseField(columnName = "points")
    private Integer points;

    // TODO: Remove this field
    @Deprecated
    @JsonIgnore
    @DatabaseField(columnName = "amount_of_subjects")
    private Integer amountOfSubjects;

    @DatabaseField(columnName = "active")
    private Boolean active;

    @DatabaseField(columnName = "created_at", readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(columnName = "updated_at", readOnly = true)
    private Timestamp updatedAt;

    public boolean isSouthHemisphere() {
        return Optional.ofNullable(southHemisphere).orElse(Boolean.FALSE);
    }

    public boolean hasYearTo() {
        return Optional.ofNullable(yearTo).isPresent();
    }

    public boolean isActive() {
        return Optional.ofNullable(active).orElse(Boolean.FALSE);
    }

    public void activate() {
        this.active = Boolean.TRUE;
    }
}
