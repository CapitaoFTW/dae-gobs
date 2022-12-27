package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.api.GobsEntityException;

public class GobsEntityExistsException extends GobsEntityException {
    public GobsEntityExistsException() {
    }

    public GobsEntityExistsException(String message) {
        super(message);
    }

    public GobsEntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public GobsEntityExistsException(Throwable cause) {
        super(cause);
    }

    public GobsEntityExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GobsEntityExistsException(Object entity) {
        super(entity);
    }

    public GobsEntityExistsException(Object entity, String message) {
        super(entity, message);
    }

    public GobsEntityExistsException(Object entity, String message, Throwable cause) {
        super(entity, message, cause);
    }

    public GobsEntityExistsException(Object entity, Throwable cause) {
        super(entity, cause);
    }

    public GobsEntityExistsException(Object entity, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(entity, message, cause, enableSuppression, writableStackTrace);
    }
}
