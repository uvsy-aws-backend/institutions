package app.uvsy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DataType;
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

    @JsonIgnore
    @DatabaseField(columnName = "subject_id")
    private String subjectId;

    @JsonIgnore
    @DatabaseField(foreign = true, readOnly = true)
    private Subject subject;

    @DatabaseField(columnName = "correlative_subject_id")
    private String correlativeSubjectId;

    @DatabaseField(columnName = "correlative_condition", dataType = DataType.ENUM_STRING)
    private CorrelativeCondition correlativeCondition;

    @DatabaseField(columnName = "correlative_restriction", dataType = DataType.ENUM_STRING)
    private CorrelativeRestriction correlativeRestriction;

    @DatabaseField(columnName = "created_at", readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(columnName = "updated_at", readOnly = true)
    private Timestamp updatedAt;
}
