package de.avalax.mtg_insight.domain.model.exception;

public class CardNotFoundException extends ResourceNotFoundException {
    public CardNotFoundException(Throwable cause) {
        super(cause);
    }

    public CardNotFoundException() {
        super();
    }
}
