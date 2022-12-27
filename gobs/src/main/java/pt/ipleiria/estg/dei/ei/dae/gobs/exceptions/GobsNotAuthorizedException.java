package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.api.GobsEntityException;

@SuppressWarnings("unused")
public class GobsNotAuthorizedException extends GobsEntityException {
    public GobsNotAuthorizedException() {
    }

    public GobsNotAuthorizedException(String message) {
        super(message);
    }

    public GobsNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GobsNotAuthorizedException(Throwable cause) {
        super(cause);
    }

    public GobsNotAuthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GobsNotAuthorizedException(Object entity) {
        super(entity);
    }

    public GobsNotAuthorizedException(Object entity, String message) {
        super(entity, message);
    }

    public GobsNotAuthorizedException(Object entity, String message, Throwable cause) {
        super(entity, message, cause);
    }

    public GobsNotAuthorizedException(Object entity, Throwable cause) {
        super(entity, cause);
    }

    public GobsNotAuthorizedException(Object entity, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(entity, message, cause, enableSuppression, writableStackTrace);
    }
}
