package app.uvsy.queries.exceptions;

public class QueryException extends RuntimeException {

    public QueryException(Throwable throwable) {
        super(throwable);
    }
}
