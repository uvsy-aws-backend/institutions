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
@DatabaseTable(tableName = "institution")
public class Institution {


    @DatabaseField(columnName = "id", id = true, readOnly = true)
    private String id;

    @DatabaseField(columnName = "name")
    private String name;

    @DatabaseField(columnName = "codename")
    private String codename;

    @DatabaseField(columnName = "active")
    private Boolean active;

    @DatabaseField(columnName = "created_at", readOnly = true)
    private Timestamp createdAt;

    @DatabaseField(columnName = "updated_at", readOnly = true)
    private Timestamp updatedAt;

    @JsonIgnore
    @ForeignCollectionField(eager = true, foreignFieldName = "institution")
    private ForeignCollection<Career> careers;

    public boolean isActive() {
        return Optional.ofNullable(active).orElse(Boolean.FALSE);
    }

    public void activate() {
        this.active = Boolean.TRUE;
    }

    @Override
    public String toString() {
        return "Institution{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", codename='" + codename + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", careers=" + careers +
                '}';
    }
}
