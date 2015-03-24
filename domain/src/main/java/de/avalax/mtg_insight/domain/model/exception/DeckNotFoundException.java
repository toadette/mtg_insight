package de.avalax.mtg_insight.domain.model.exception;

public class DeckNotFoundException extends RessourceNotFoundException {
    public DeckNotFoundException(Throwable e) {
        super(e);
    }
}
