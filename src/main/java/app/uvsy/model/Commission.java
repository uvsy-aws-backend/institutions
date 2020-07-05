package app.uvsy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@DatabaseTable(tableName = "commission")
public class Commission {

    @DatabaseField(columnName = "id", id = true, readOnly = true)
    private String id;

    @DatabaseField(columnName = "name")
    private String name;

    @JsonIgnore
    @DatabaseField(columnName = "program_id")
    private String programId;

    @DatabaseField(columnName = "active")
    private Boolean active;

    @DatabaseField(columnName = "level")
    private Integer level;

    @DatabaseField(columnName = "created_at", readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(columnName = "updated_at", readOnly = true)
    private Timestamp updatedAt;
}
