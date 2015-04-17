package de.avalax.mtg_insight.port.adapter.service.deck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.CardService;
import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.domain.model.deck.Deckname;
import de.avalax.mtg_insight.domain.model.deck.StandardDeck;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.domain.model.exception.DeckNotFoundException;

public class TappedOutDeckService implements DeckService {

    //TODO: format von txt auf Markdown?
    private List<Deckname> decknames;
    private final String host = "http://tappedout.net/mtg-decks/";
    private final String format = "/?fmt=txt";
    private final String format_printable = "?fmt=markdown";
    private CardService cardService;

    public TappedOutDeckService(CardService cardService) {
        this.cardService = cardService;
        decknames = new ArrayList<>();
    }

    private Deck readFromTextFile(String name) throws DeckNotFoundException {
        decknames.add(new Deckname(name));
        List<Card> deck = new ArrayList<>();
        List<Card> sideboard = new ArrayList<>();
        readCardsFromDeck(name, deck, sideboard);
        name = readPrintableDeckname(name);
        return new StandardDeck(new Deckname(name), deck, sideboard);
    }

    private String readPrintableDeckname(String name) throws DeckNotFoundException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(host + name + format_printable).openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    name = line.substring(line.indexOf("[") + 1, line.lastIndexOf("]"));
                    break;
                }
            }
        } catch (IOException e) {
            throw new DeckNotFoundException(e);
        }
        return name;
    }

    private void readCardsFromDeck(String name, List<Card> deck, List<Card> sideboard) throws DeckNotFoundException {
        boolean readSideBoard = false;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(host + name + format).openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    if (line.startsWith("Sideboard")) {
                        readSideBoard = true;
                        continue;
                    }
                    if (readSideBoard) {
                        addCardFromLine(line, sideboard);
                    } else {
                        addCardFromLine(line, deck);
                    }

                }
            }
        } catch (IOException e) {
            throw new DeckNotFoundException(e);
        }
    }

    private void addCardFromLine(String line, List<Card> cardOfDeck) {
        String[] split = line.split("\\t");
        int count = Integer.valueOf(split[0]);
        String name = split[1];

        if (cardService == null) {
            for (int i = 0; i < count; i++) {
                cardOfDeck.add(new CardBuilder(name).build());
            }
        } else {
            Card card;
            try {
                card = cardService.cardFromCardname(name);
            } catch (CardNotFoundException ignored) {
                card = new CardBuilder(name).build();
            }
            for (int i = 0; i < count; i++) {
                cardOfDeck.add(card);
            }
        }
    }

    @Override
    public Deck deckFromDeckname(Deckname deckname) throws DeckNotFoundException {
        return readFromTextFile(deckname.getName());
    }

    @Override
    public List<Deckname> decknames() {
        return decknames;
    }
}
