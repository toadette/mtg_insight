package de.avalax.mtg_insight.domain.model.exception;

public class CardCorruptedException extends ResourceNotFoundException {
    public CardCorruptedException(Throwable cause) {
        super(cause);
    }

    public CardCorruptedException() {
        super();
    }
}
