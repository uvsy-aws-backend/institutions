package app.uvsy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@DatabaseTable(tableName = "correlative")
public class Correlative {

    @DatabaseField(columnName = "id", id = true, readOnly = true)
    private String id;

    @DatabaseField(columnName = "subject_id")
    private String subjectId;

    @JsonIgnore
    @DatabaseField(foreign = true, readOnly = true)
    private Subject subject;

    @DatabaseField(columnName = "target_id")
    private String targetId;

    @JsonIgnore
    @DatabaseField(foreign = true, readOnly = true)
    private Subject target;

    @DatabaseField(columnName = "created_at", readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(columnName = "updated_at", readOnly = true)
    private Timestamp updatedAt;
}
