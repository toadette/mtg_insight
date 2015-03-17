package de.avalax.mtg_insight.domain.model.deck;


import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;

public class StandardDeck extends BasicDeck {
    public StandardDeck(Deckname deckname, List<Card> cardOfDeck){
        super(deckname,cardOfDeck);
    }
}
