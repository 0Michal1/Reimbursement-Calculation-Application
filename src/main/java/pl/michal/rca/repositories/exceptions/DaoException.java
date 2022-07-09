package pl.michal.rca.repositories.exceptions;

public class DaoException extends RuntimeException {

    public DaoException(String msg, Exception cause) {
        super(msg, cause);
    }
}
