package de.avalax.mtg_insight.application.port.adapter;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardService;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;

public class CachedCardService implements CardService {
    private final CardService cardService;
    private final CacheStrategy cacheStrategy;

    public CachedCardService(CardService cardService, CacheStrategy cacheStrategy) {
        this.cardService = cardService;
        this.cacheStrategy = cacheStrategy;
    }

    @Override
    public Card cardFromCardname(String cardname) throws CardNotFoundException {
        if (cardname == null || cardname.isEmpty()) {
            throw new CardNotFoundException();
        }
        Card cardFromCache = cacheStrategy.get(cardname);
        if (cardFromCache != null) {
            return cardFromCache;
        }
        Card card = cardService.cardFromCardname(cardname);
        cacheStrategy.put(cardname, card);
        return card;
    }
}
