package de.avalax.mtg_insight.port.adapter.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;
import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.domain.model.deck.Deckname;
import de.avalax.mtg_insight.domain.model.deck.StandardDeck;

public class TappedOutDeckService implements DeckService {

    private List<Deckname> decknames;
    private final String host = "http://tappedout.net/mtg-decks/";
    private final String format = "/?fmt=txt";
    private CardService cardService;

    public TappedOutDeckService(CardService cardService) {
        this.cardService = cardService;
        decknames = new ArrayList<>();
    }

    private Deck readFromFile(String name) throws IOException {
        decknames.add(new Deckname(name));
        URL url = new URL(host + name + format);
        BufferedReader reader = null;
        List<Card> cardOfDeck = new ArrayList<>();
        try {
            String line = null;
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    addCardFromLine(line, cardOfDeck);
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return new StandardDeck(new Deckname(name), cardOfDeck);
    }

    private void addCardFromLine(String line, List<Card> cardOfDeck) {
        String[] split = line.split("\\t");
        int count = Integer.valueOf(split[0]);
        String name = split[1];
        for (int i = 0; i < count; i++) {
            cardOfDeck.add(new Creature(name,null,null,null));
        }
    }

    @Override
    public Deck deckFromDeckname(Deckname deckname) {
        Deck deck;
        try {
            deck = readFromFile(deckname.getName());
        } catch (Exception e) {
            throw new RuntimeException("Error TappedOutDeckService: " + e.getCause().getLocalizedMessage());
        }
        return deck;
    }

    @Override
    public List<Deckname> decknames() {
        return decknames;
    }
}
