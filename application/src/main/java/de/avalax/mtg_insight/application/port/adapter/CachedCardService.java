package de.avalax.mtg_insight.application.port.adapter;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardService;
import de.avalax.mtg_insight.domain.model.exception.CardCorruptedException;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.domain.model.exception.InvalidCardnameException;

public class CachedCardService implements CardService {
    private final CardService cardService;
    private final CacheStrategy cacheStrategy;

    public CachedCardService(CardService cardService, CacheStrategy cacheStrategy) {
        this.cardService = cardService;
        this.cacheStrategy = cacheStrategy;
    }

    @Override
    public Card cardFromCardname(String cardname) throws CardNotFoundException, CardCorruptedException {
        if (invalidCardname(cardname)) {
            throw new InvalidCardnameException();
        }
        return fetchCard(cardname);
    }

    private Card fetchCard(String cardname) throws CardNotFoundException, CardCorruptedException {
        Card card;
        try {
            card = cacheStrategy.get(cardname);
        } catch (CardNotFoundException ignored) {
            card = cardService.cardFromCardname(cardname);
            cacheStrategy.put(card);
        }
        return card;
    }

    private boolean invalidCardname(String cardname) {
        return cardname == null || cardname.isEmpty();
    }
}
