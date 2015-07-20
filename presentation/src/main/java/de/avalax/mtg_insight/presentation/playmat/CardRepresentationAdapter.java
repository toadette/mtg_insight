package de.avalax.mtg_insight.presentation.playmat;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import de.avalax.mtg_insight.application.calculation.CardCalculationService;
import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.application.representation.CardRepresentationComparator;
import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.deck.CardCollection;
import de.avalax.mtg_insight.presentation.card.CardView;

public class CardRepresentationAdapter implements Iterable<CardView> {
    private List<CardView> cardViews;

    public CardRepresentationAdapter(Context context, List<?> deck, CardCalculationService cardCalculationService) {
        cardViews = new ArrayList<>();

        SortedMap<String, CardRepresentation> groupedCards = new TreeMap<>();

        for (Object genericCard : deck) {
            Card card = (Card) genericCard;
            if (groupedCards.containsKey(card.name())) {
                groupedCards.get(card.name()).setCountOfCard(groupedCards.get(card.name()).count() + 1);
            } else {
                CardRepresentation cardRepresentation = new CardRepresentation(card);
                //TODO: dynamic turns via UI?
                cardRepresentation.setDrawPercentage(cardCalculationService.cardDrawPercentage(1,card));
                groupedCards.put(card.name(), cardRepresentation);
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
