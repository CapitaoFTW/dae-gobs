package pt.ipleiria.estg.dei.ei.dae.occurrencemanagementinsuredassets.exceptions.api;

@SuppressWarnings("unused")
public class EntityException extends Exception {
    private Object entity;

    public EntityException() {
    }

    public EntityException(String message) {
        super(message);
    }

    public EntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityException(Throwable cause) {
        super(cause);
    }

    public EntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntityException(Object entity) {
        this.entity = entity;
    }

    public EntityException(Object entity, String message) {
        super(message);
        this.entity = entity;
    }

    public EntityException(Object entity, String message, Throwable cause) {
        super(message, cause);
        this.entity = entity;
    }

    public EntityException(Object entity, Throwable cause) {
        super(cause);
        this.entity = entity;
    }

    public EntityException(Object entity, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.entity = entity;
    }

    public Object getEntity() {
        return entity;
    }

    public String getComposedMessage() {
        if (this.entity == null)
            return getMessage();

        return String.format("%s. Entity: %s", getMessage(), getEntity());
    }
}
