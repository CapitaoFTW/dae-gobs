package pt.ipleiria.estg.dei.ei.dae.gobs.exceptions.api;

@SuppressWarnings("unused")
public class GobsEntityException extends RuntimeException {
    private Object entity;

    public GobsEntityException() {
    }

    public GobsEntityException(String message) {
        super(message);
    }

    public GobsEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public GobsEntityException(Throwable cause) {
        super(cause);
    }

    public GobsEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GobsEntityException(Object entity) {
        this.entity = entity;
    }

    public GobsEntityException(Object entity, String message) {
        super(message);
        this.entity = entity;
    }

    public GobsEntityException(Object entity, String message, Throwable cause) {
        super(message, cause);
        this.entity = entity;
    }

    public GobsEntityException(Object entity, Throwable cause) {
        super(cause);
        this.entity = entity;
    }

    public GobsEntityException(Object entity, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
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
