package app.uvsy.model.query;

import app.uvsy.model.Career;
import app.uvsy.model.Institution;
import lombok.Data;

@Data
public class ProgramInfo {

    private final String programId;
    private Institution institution;
    private Career career;
}
