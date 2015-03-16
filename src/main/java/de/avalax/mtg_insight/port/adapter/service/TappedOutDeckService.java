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

import de.avalax.mtg_insight.domain.model.deck.Deck;
import de.avalax.mtg_insight.domain.model.deck.DeckService;
import de.avalax.mtg_insight.domain.model.deck.Deckname;

public class TappedOutDeckService implements DeckService{

    private List<Deckname> decknames;
    public TappedOutDeckService(String url){
        decknames=new ArrayList<>();
        try {
            readFromFile(url);
        }catch(Exception e){
            throw new RuntimeException("Error TappedOutDeckService: "+e.getCause().getLocalizedMessage());
        }
    }

    private void readFromFile(String uri) throws IOException {
        URL url= new URL(uri);
        InputStreamReader inputStreamReader= new InputStreamReader(url.openStream());
        String line = null;
        BufferedReader reader = null;
        StringBuilder result=new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            line=result.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        decknames.add(new Deckname("test "+line));
    }
    @Override
    public Deck deckFromDeckname(Deckname deck) {
        return null;
    }

    @Override
    public List<Deckname> decknames() {
        return decknames;
    }
}
