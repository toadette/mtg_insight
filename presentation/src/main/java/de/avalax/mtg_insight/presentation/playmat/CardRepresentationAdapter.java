package de.avalax.mtg_insight.presentation.playmat;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.application.representation.CardRepresentationComparator;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.presentation.card.CardView;

public class CardRepresentationAdapter implements Iterable<CardView> {
    private List<CardView> cardViews;

    public CardRepresentationAdapter(Context context, List<?> deck) {
        cardViews = new ArrayList<>();

        SortedMap<String, CardRepresentation> groupedCards = new TreeMap<>();

        for (Object genericCard : deck) {
            Card card = (Card) genericCard;
            if (groupedCards.containsKey(card.name())) {
                groupedCards.get(card.name()).setCountOfCard(groupedCards.get(card.name()).count() + 1);
            } else {
                groupedCards.put(card.name(), new CardRepresentation(card));
            }
        }
        List<CardRepresentation> sortedCardRepresentations = new ArrayList<>();
        sortedCardRepresentations.addAll(groupedCards.values());
        Collections.sort(sortedCardRepresentations, new CardRepresentationComparator());
        for (CardRepresentation card : sortedCardRepresentations) {
            cardViews.add(new CardView(context, card));
        }
    }

    @Override
    public Iterator<CardView> iterator() {
        return cardViews.iterator();
    }
}
