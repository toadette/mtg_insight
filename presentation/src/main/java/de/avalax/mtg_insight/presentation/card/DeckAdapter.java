package de.avalax.mtg_insight.presentation.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import de.avalax.mtg_insight.application.representation.CardRepresentation;
import de.avalax.mtg_insight.domain.model.card.Card;

@Deprecated
public class DeckAdapter extends BaseAdapter {
    private List<CardRepresentation> cards;
    private Context context;

    public DeckAdapter(Context context, List<?> deck) {
        this.context = context;
        cards = new ArrayList<>();
        SortedMap<String, CardRepresentation> groupedCards = new TreeMap<>();


        for (Object genericCard : deck) {
            Card card = (Card) genericCard;
            if (groupedCards.containsKey(card.name())) {
                groupedCards.get(card.name()).setCountOfCard(groupedCards.get(card.name()).count() + 1);
            } else {
                groupedCards.put(card.name(), new CardRepresentation(card));
            }
        }

        cards.addAll(groupedCards.values());
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @Override
    public Object getItem(int position) {
        return cards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return new CardView(context, cards.get(position));
    }
}
