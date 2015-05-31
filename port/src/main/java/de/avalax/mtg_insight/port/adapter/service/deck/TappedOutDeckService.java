package de.avalax.mtg_insight.port.adapter.service.deck;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.CardService;
import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.domain.model.deck.Deckname;
import de.avalax.mtg_insight.domain.model.deck.JobProgressListener;
import de.avalax.mtg_insight.domain.model.deck.StandardDeck;
import de.avalax.mtg_insight.domain.model.exception.CardCorruptedException;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.domain.model.exception.DeckNotFoundException;

public class TappedOutDeckService implements DeckService {

    //TODO: format von txt auf Markdown?
    //TODO:refactoring
    private List<Deckname> decknames;
    private final String host = "http://tappedout.net/mtg-decks/";
    private final String format = "/?fmt=txt";
    private final String format_printable = "/?fmt=markdown";
    private CardService cardService;


    public TappedOutDeckService(CardService cardService) {
        this.cardService = cardService;
        decknames = new ArrayList<>();
    }

    private Deck readFromTextFile(String name, JobProgressListener listener, int currentCard) throws DeckNotFoundException {
        decknames.add(new Deckname(name));
        List<Card> deck = new ArrayList<>();
        List<Card> sideboard = new ArrayList<>();
        String deckname = readPrintableDeckname(name);
        readCardsFromDeck(name, deck, sideboard, listener, currentCard);
        return new StandardDeck(new Deckname(deckname), deck, sideboard);
    }

    private String readPrintableDeckname(String name) throws DeckNotFoundException {
        try {
            URLConnection conn = new URL(host + name + format_printable).openConnection();

            conn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            int cnt = 0;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    if (cnt == 0) {
                        name = line.substring(line.indexOf("[") + 1, line.lastIndexOf("]"));
                        break;
                    }
                    ++cnt;

                }
            }
        } catch (IOException e) {
            throw new DeckNotFoundException(e);
        }
        return name;
    }

    private void readCardsFromDeck(String name, List<Card> deck, List<Card> sideboard, JobProgressListener listener, int currentCard) throws DeckNotFoundException {

        boolean readSideBoard = false;
        try {

            URLConnection conn = new URL(host + name + format).openConnection();

            conn.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    if (line.startsWith("Sideboard")) {
                        readSideBoard = true;
                        continue;
                    }
                    if (readSideBoard) {
                        currentCard = addCardFromLine(line, sideboard, currentCard);
                    } else {
                        currentCard = addCardFromLine(line, deck, currentCard);
                    }
                }
                //TODO:
                listener.publishProgress(currentCard);
            }
        } catch (IOException e) {
            throw new DeckNotFoundException(e);
        }
    }

    private int addCardFromLine(String line, List<Card> cardOfDeck, int currentCard) {
        String[] split = getCardInformationArray(line);
        int count = getCardCount(split);
        String name = getCardName(split);

        if (cardService == null) {
            addCardsToDeck(cardOfDeck, count, name, new CardBuilder(name).build());
        } else {
            Card card;
            try {
                card = cardService.cardFromCardname(name);
            } catch (CardNotFoundException ignored) {
                card = new CardBuilder(name).build();
            } catch (CardCorruptedException ignored) {
                card = new CardBuilder(name).build();
            }
            addCardsToDeck(cardOfDeck, count, name, card);
        }
        currentCard += count;
        return currentCard;
    }

    private String getCardName(String[] split) {
        return split[1];
    }

    private Integer getCardCount(String[] split) {
        return Integer.valueOf(split[0]);
    }

    private String[] getCardInformationArray(String line) {
        return line.split("\\t");
    }

    private void addCardsToDeck(List<Card> cardOfDeck, int count, String name, Card build) {
        for (int i = 0; i < count; i++) {
            cardOfDeck.add(build);
        }
    }

    @Override
    public Deck deckFromDeckname(Deckname deckname, JobProgressListener listener) throws DeckNotFoundException {
        int currentCard = 0;
        return readFromTextFile(deckname.getName(), listener, currentCard);
    }

    @Override
    public List<Deckname> decknames() {
        return decknames;
    }


}
