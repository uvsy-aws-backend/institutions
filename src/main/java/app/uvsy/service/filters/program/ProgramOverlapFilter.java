package app.uvsy.service.filters.program;

import app.uvsy.model.Program;

import java.util.function.Function;

public class ProgramOverlapFilter implements Function<Program, Boolean> {

    private final Program refProgram;

    public ProgramOverlapFilter(Program refProgram) {
        this.refProgram = refProgram;
    }

    @Override
    public Boolean apply(Program program) {
        if (program.hasYearTo() && refProgram.hasYearTo()) {
            return program.getYearFrom() <= refProgram.getYearTo() &&
                    program.getYearTo() >= refProgram.getYearFrom();
        } else if (program.hasYearTo()) {
            return program.getYearTo() >= refProgram.getYearFrom();
        } else if (refProgram.hasYearTo()) {
            return refProgram.getYearTo() >= program.getYearFrom();
        }
        return Boolean.TRUE;
    }
}
