package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.exception.CardCorruptedException;
import de.avalax.mtg_insight.domain.model.exception.CardNotFoundException;
import de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer;
import de.avalax.mtg_insight.domain.model.card.CardService;
import de.avalax.mtg_insight.port.adapter.service.color.ColorMatcher;
import de.avalax.mtg_insight.port.adapter.service.manaCost.ManaTokenizer;

public class GathererCardService implements CardService {

    private final String host = "http://gatherer.wizards.com/Pages/Search/Default.aspx?name=";
    private final ManaTokenizer manaTokenizer;
    private final ColorMatcher colorMatcher;
    private final AbilityTokenizer abilityTokenizer;

    public GathererCardService(ManaTokenizer manaTokenizer, ColorMatcher colorMatcher, AbilityTokenizer abilityTokenizer) {

        this.manaTokenizer = manaTokenizer;
        this.colorMatcher = colorMatcher;
        this.abilityTokenizer = abilityTokenizer;
    }

    @Override
    public Card cardFromCardname(String cardname) throws CardNotFoundException, CardCorruptedException {
        Document doc = null;
        try {
            doc = Jsoup.connect(host + getCardNameForSearch(cardname)).get();
            GathererHelper gathererHelper = new GathererHelper(abilityTokenizer, colorMatcher, manaTokenizer);
            return gathererHelper.getCard(getType(doc), getName(doc), getDescription(doc), getManaFromElement(getMana(doc)), getPowerToughness(doc));
        } catch (CardCorruptedException e) {
            throw new CardCorruptedException(e);
        } catch (Exception e) {
            Card cardResultinList = null;
            try {
                cardResultinList = findCardResultinList(doc, cardname);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (cardResultinList == null) {
                throw new CardNotFoundException(e);
            }
            return cardResultinList;
        }
    }

    private Card findCardResultinList(Document doc, String cardname) throws IOException, CardCorruptedException {
        if (doc != null) {
            Elements cardItemTable = doc.getElementsByClass("cardItemTable");
            if (cardItemTable != null) {
                Element element = cardItemTable.get(0).child(0).child(0).child(0);
                for (Element child : element.children()) {
                    Element child1 = child.child(0).child(0);
                    Elements middleCol = child1.getElementsByClass("middleCol");
                    if (middleCol != null) {
                        Element cardInfo = middleCol.get(0).child(1);
                        if (cardInfo != null) {
                            Elements cardTitle = cardInfo.getElementsByClass("cardTitle");
                            if (cardTitle != null) {
                                String name = cardTitle.get(0).child(0).text();
                                Element href = cardTitle.get(0).child(0).attr("href", "");
                                cardTitle.get(0).child(0).attr("href");
                                if (name.equals(cardname)) {
                                    String attr = child.child(0).child(0).getElementsByClass("leftCol").get(0).child(1).attr("href");
                                    Pattern pattern = Pattern.compile(".\\?multiverseid.");
                                    Matcher matcher = pattern.matcher(attr);
                                    if (matcher.find()) {
                                        String substring = attr.substring(attr.indexOf("?") + 1, attr.length());
                                        String url = "http://gatherer.wizards.com/Pages/Card/Details.aspx?" + substring;
                                        doc = Jsoup.connect(url).get();
                                        GathererHelper gathererHelper = new GathererHelper(abilityTokenizer, colorMatcher, manaTokenizer);
                                        return gathererHelper.getCard(getType(doc), getName(doc), getDescription(doc), getManaFromElement(getMana(doc)), getPowerToughness(doc));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private Element getElementById(Document doc, String id) {
        if (doc.body().getElementById(id) != null && doc.body().getElementById(id).children() != null && doc.body().getElementById(id).children().size() > 0) {
            return doc.body().getElementById(id).children().get(1);
        }
        return null;
    }

    private Element getMana(Document doc) {
        return getElementById(doc, GathererConstants.MANA);
    }

    private String getPowerToughness(Document doc) {
        Element elementById = getElementById(doc, GathererConstants.POWER_TOUGHNESS);
        if (elementById != null) {
            return elementById.text();
        }
        return "";
    }

    private String getDescription(Document doc) {
        Element elementById = getElementById(doc, GathererConstants.DESCRIPTION_TEXT);
        if (elementById != null && elementById.children().size() > 0 && elementById.child(0).childNodes().size() > 0) {
            return elementById.child(0).childNodes().get(0).toString();
        }
        return "";
    }

    private String getName(Document doc) {
        return getElementById(doc, GathererConstants.NAME).text();
    }

    private String getType(Document doc) {
        return getElementById(doc, GathererConstants.TYPE).text();
    }

    private List<String> getManaFromElement(Element manaRow) {
        if (manaRow != null) {
            List<String> manaList = new ArrayList<>();
            for (int i = 0; i < manaRow.children().size(); i++) {
                manaList.add(manaRow.children().get(i).attr("alt"));
            }
            return manaList;
        }
        return Collections.emptyList();
    }

    private String getCardNameForSearch(String cardname) {
        String result = "";
        String[] split = cardname.split(" ");
        for (String str : split) {
            result += "+[";
            result += str;
            result += "]";
        }
        return result;
    }


}
