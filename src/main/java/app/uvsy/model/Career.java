package app.uvsy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Optional;

@Data
@DatabaseTable(tableName = "career")
public class Career {

    @DatabaseField(columnName = "id", id = true, readOnly = true)
    private String id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "codename")
    private String codename;

    @DatabaseField(columnName = "active")
    private Boolean active;

    @DatabaseField(columnName = "institution_id")
    private String institutionId;

    @JsonIgnore
    @DatabaseField(foreign = true, readOnly = true)
    private Institution institution;

    @DatabaseField(columnName = "created_at", readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(columnName = "updated_at", readOnly = true)
    private Timestamp updatedAt;

    @JsonIgnore
    @ForeignCollectionField(eager = true, foreignFieldName = "career")
    private ForeignCollection<Program> programs;

    public boolean isActive() {
        return Optional.ofNullable(active).orElse(Boolean.FALSE);
    }

    public void activate() {
        this.active = Boolean.TRUE;
    }
}
