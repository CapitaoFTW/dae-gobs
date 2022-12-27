package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions;

import pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.api.EntityException;

@SuppressWarnings("unused")
public class EntityNotFoundException extends EntityException {
    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntityNotFoundException(Object entity) {
        super(entity);
    }

    public EntityNotFoundException(Object entity, String message) {
        super(entity, message);
    }

    public EntityNotFoundException(Object entity, String message, Throwable cause) {
        super(entity, message, cause);
    }

    public EntityNotFoundException(Object entity, Throwable cause) {
        super(entity, cause);
    }

    public EntityNotFoundException(Object entity, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(entity, message, cause, enableSuppression, writableStackTrace);
    }
}
