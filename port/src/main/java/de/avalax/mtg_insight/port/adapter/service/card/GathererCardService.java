package de.avalax.mtg_insight.port.adapter.service.card;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.card.GenericCard;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

public class GathererCardService implements CardService {

    public GathererCardService(ManaTokenizer manaTokenizer, ColorMatcher colorMatcher, AbilityTokenizer abilityTokenizer) {

    }

    @Override
    public Card cardFromCardname(String cardname) throws CardNotFoundException {
        String url = "http://gatherer.wizards.com/Pages/Search/Default.aspx?name=+[Brimaz]+[King]+[of]+[Oreskos]";
        String url2 = "http://gatherer.wizards.com/Pages/Search/Default.aspx?name=+[Dragonlord]+[Ojutai]";
        String url3 = "http://gatherer.wizards.com/Pages/Search/Default.aspx?name=+[Dig]+[Through]+[Time]";
        String url4 = "http://gatherer.wizards.com/Pages/Search/Default.aspx?name=+[Narset]+[Transcendent]";
        Document doc;
        try {
            test(url);
            System.out.println("__________________________");
            test(url2);
            System.out.println("__________________________");
            test(url3);
            System.out.println("__________________________");
            test(url4);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CardBuilder(cardname).build();
    }

    private void test(String url) throws IOException {
        Document doc;
        doc = Jsoup.connect(url).get();
        System.out.println(doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_nameRow").children().get(1).text());
        System.out.println(doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_typeRow").children().get(1).text());
        Element manaRow = doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_manaRow").children().get(1);
        for (int i = 0; i < manaRow.children().size(); i++) {
            System.out.print(manaRow.children().get(i).attr("alt") + " ");
        }
        System.out.println();
        Element description = doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_textRow").children().get(1);
        for (int i = 0; i < description.children().size(); i++) {
            System.out.println(description.children().get(i).text());
        }
        Element powerToughness = doc.body().getElementById("ctl00_ctl00_ctl00_MainContent_SubContent_SubContent_ptRow");
        if (powerToughness != null) {
            System.out.println(powerToughness.children().get(1).text());
        }
    }
}
