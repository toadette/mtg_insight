package de.avalax.mtg_insight.presentation.persistence;

import android.util.Log;

import de.avalax.mtg_insight.application.port.adapter.CacheStrategy;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardRepository;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;

public class AndroidCacheStrategy implements CacheStrategy {
    private CardRepository cardRepository;

    public AndroidCacheStrategy(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public void put(Card card) {
        if (isGenericCard(card)) {
            Log.e("skipping generic card", card.name());
            return;
        }
        cardRepository.save(card);
    }

    private boolean isGenericCard(Card card) {
        return card.getClass().getSimpleName().equals("GenericCard");
    }

    @Override
    public Card get(String cardname) throws CardNotFoundException {
        return cardRepository.load(cardname);
    }
}
