package app.uvsy.database.exceptions;

public class ItemDoesNotExistException extends DBException {

    public ItemDoesNotExistException(Throwable throwable) {
        super(throwable);
    }
}
