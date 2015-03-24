package de.avalax.mtg_insight.domain.model.exception;

public class CardNotFoundException extends RessourceNotFoundException {
    public CardNotFoundException(Throwable cause) {
        super(cause);
    }
}
