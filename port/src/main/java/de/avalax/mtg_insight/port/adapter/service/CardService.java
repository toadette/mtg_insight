package de.avalax.mtg_insight.port.adapter.service;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import de.avalax.mtg_insight.domain.model.card.Card;

public interface CardService {
    Card cardFromCardname(String cardname) throws IOException, ParseException;
}
