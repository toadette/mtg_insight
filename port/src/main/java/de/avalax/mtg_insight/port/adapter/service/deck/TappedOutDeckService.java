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
    private final String format_printable = "?fmt=markdown";
    private CardService cardService;
    private int currentCard = 0;
    private int decksize = 0;


    public TappedOutDeckService(CardService cardService) {
        this.cardService = cardService;
        decknames = new ArrayList<>();
    }

    private Deck readFromTextFile(String name, JobProgressListener listener) throws DeckNotFoundException {
        decknames.add(new Deckname(name));
        List<Card> deck = new ArrayList<>();
        List<Card> sideboard = new ArrayList<>();
        String deckname = readPrintableDeckname(name);
        readCardsFromDeck(name, deck, sideboard, listener);
        return new StandardDeck(new Deckname(deckname), deck, sideboard);
    }

    private String readPrintableDeckname(String name) throws DeckNotFoundException {
        String mainboardsize = null;
        String sideboardsize = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(host + name + format_printable).openStream()))) {
            String line;
            int cnt = 0;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 0) {
                    if (cnt == 0) {
                        name = line.substring(line.indexOf("[") + 1, line.lastIndexOf("]"));
                    }
                    if (line.startsWith("##")) {
                        String board = line.substring(line.indexOf("##") + 3, line.length());
                        if (board.startsWith("Mainboard")) {
                            mainboardsize = line.substring(line.indexOf("(") + 1, line.length() - 1);
                        }
                        if (board.startsWith("Sideboard")) {
                            sideboardsize = line.substring(line.indexOf("(") + 1, line.length() - 1);
                            break;
                        }
                    }
                    ++cnt;

                }
            }
        } catch (IOException e) {
            throw new DeckNotFoundException(e);
        }
        if (mainboardsize != null && mainboardsize.matches(".\\d")) {
            int mainBoardSize = Integer.valueOf(mainboardsize);
            int sideBoardSize = 0;
            if (sideboardsize != null && sideboardsize.matches(".\\d")) {
                sideBoardSize = Integer.valueOf(sideBoardSize);
            }
            decksize = mainBoardSize + sideBoardSize;
        }
        return name;
    }

    private void readCardsFromDeck(String name, List<Card> deck, List<Card> sideboard, JobProgressListener listener) throws DeckNotFoundException {

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
                    ++currentCard;
                }
                listener.publishProgress((currentCard / decksize) * 100);
            }
        } catch (IOException e) {
            throw new DeckNotFoundException(e);
        }
    }

    private void addCardFromLine(String line, List<Card> cardOfDeck) {
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
//                log.error("Card was not found: "+name+": "+ignored.getMessage());
                card = new CardBuilder(name).build();
            } catch (CardCorruptedException ignored) {
//                log.error("Card was corrupted: "+name+": "+ignored.getMessage());
                card = new CardBuilder(name).build();
            }
            addCardsToDeck(cardOfDeck, count, name, card);
        }
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
        return readFromTextFile(deckname.getName(), listener);
    }

    @Override
    public List<Deckname> decknames() {
        return decknames;
    }

    @Override
    public double getCurrentLoadingCount() {
        return currentCard;
    }

    @Override
    public double countOfCards() {
        return decksize;
    }


}
