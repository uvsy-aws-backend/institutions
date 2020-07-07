package app.uvsy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
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

    @DatabaseField(columnName = "valid_from")
    private Date validFrom;

    @DatabaseField(columnName = "valid_to")
    private Date validTo;

    @DatabaseField(columnName = "hours")
    private Integer hours;

    @DatabaseField(columnName = "points")
    private Integer points;

    @DatabaseField(columnName = "amount_of_subjects")
    private Integer amountOfSubjects;

    @DatabaseField(columnName = "active")
    private Boolean active;

    @DatabaseField(columnName = "created_at", readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(columnName = "updated_at", readOnly = true)
    private Timestamp updatedAt;

    @DatabaseField(columnName = "deactivated_at", readOnly = true)
    private Timestamp deactivatedAt;


    public boolean isActive() {
        return Optional.ofNullable(active).orElse(Boolean.FALSE);
    }

    public void activate() {
        this.active = Boolean.TRUE;
    }

    @Override
    public String toString() {
        return "Program{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", careerId='" + careerId + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", hours=" + hours +
                ", points=" + points +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deactivatedAt=" + deactivatedAt +
                '}';
    }
}
