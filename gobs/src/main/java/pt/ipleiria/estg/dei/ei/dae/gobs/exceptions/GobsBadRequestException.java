package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.api.GobsEntityException;

@SuppressWarnings("unused")
public class GobsBadRequestException extends GobsEntityException {
    public GobsBadRequestException() {
    }

    public GobsBadRequestException(String message) {
        super(message);
    }

    public GobsBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public GobsBadRequestException(Throwable cause) {
        super(cause);
    }

    public GobsBadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GobsBadRequestException(Object entity) {
        super(entity);
    }

    public GobsBadRequestException(Object entity, String message) {
        super(entity, message);
    }

    public GobsBadRequestException(Object entity, String message, Throwable cause) {
        super(entity, message, cause);
    }

    public GobsBadRequestException(Object entity, Throwable cause) {
        super(entity, cause);
    }

    public GobsBadRequestException(Object entity, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(entity, message, cause, enableSuppression, writableStackTrace);
    }
}
