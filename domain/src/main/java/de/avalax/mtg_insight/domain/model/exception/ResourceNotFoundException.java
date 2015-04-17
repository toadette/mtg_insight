package de.avalax.mtg_insight.domain.model.exception;


public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException() {
        super();
    }
}
