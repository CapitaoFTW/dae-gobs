package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.api.GobsEntityException;

@SuppressWarnings("unused")
public class GobsEntityNotFoundException extends GobsEntityException {
    public GobsEntityNotFoundException() {
    }

    public GobsEntityNotFoundException(String message) {
        super(message);
    }

    public GobsEntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public GobsEntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public GobsEntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GobsEntityNotFoundException(Object entity) {
        super(entity);
    }

    public GobsEntityNotFoundException(Object entity, String message) {
        super(entity, message);
    }

    public GobsEntityNotFoundException(Object entity, String message, Throwable cause) {
        super(entity, message, cause);
    }

    public GobsEntityNotFoundException(Object entity, Throwable cause) {
        super(entity, cause);
    }

    public GobsEntityNotFoundException(Object entity, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(entity, message, cause, enableSuppression, writableStackTrace);
    }
}
