package de.avalax.mtg_insight.application.representation;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

import static org.assertj.core.api.Assertions.assertThat;

public class CardComparatorTest {
    private ConvertedManaCost convertedManaCost(String cmc, Mana... manaa) {
        List<ManaCost> manaCostList = new ArrayList<>();
        for (Mana mana : manaa) {
            List<Mana> manaCost = new ArrayList<>();
            manaCost.add(mana);
            manaCostList.add(new ManaCost(manaCost, ""));
        }
        return new ConvertedManaCost(cmc, manaCostList);
    }

    private CardComparator cardComparator;

    private List<Card> cards;

    @Before
    public void setUp() throws Exception {
        cards = new ArrayList<>();
        cardComparator = new CardComparator();
    }

    @Test
    public void sortEmptyList() throws Exception {
        Collections.sort(cards, cardComparator);

        assertThat(cards).isEmpty();
    }

    @Test
    public void sortSortedList() throws Exception {
        cards.add(new CardBuilder("a").build());
        cards.add(new CardBuilder("b").build());

        Collections.sort(cards, cardComparator);

        assertThat(cards.get(0).name()).isEqualTo("a");
        assertThat(cards.get(1).name()).isEqualTo("b");
    }

    @Test
    public void sortByName() throws Exception {
        cards.add(new CardBuilder("a").build());
        cards.add(new CardBuilder("c").build());
        cards.add(new CardBuilder("b").build());

        Collections.sort(cards, cardComparator);

        assertThat(cards.get(0).name()).isEqualTo("a");
        assertThat(cards.get(1).name()).isEqualTo("b");
        assertThat(cards.get(2).name()).isEqualTo("c");
    }

    @Test
    public void sortByMana() throws Exception {
        cards.add(new CardBuilder("").convertedManaCost(convertedManaCost("onemana", Mana.BLACK)).build());
        cards.add(new CardBuilder("").convertedManaCost(convertedManaCost("threemana", Mana.GREEN, Mana.BLACK, Mana.RED)).build());
        cards.add(new CardBuilder("").convertedManaCost(convertedManaCost("twomana", Mana.BLACK, Mana.BLACK)).build());

        Collections.sort(cards, cardComparator);

        assertThat(cards.get(0).convertedManaCost().toString()).isEqualTo("onemana");
        assertThat(cards.get(1).convertedManaCost().toString()).isEqualTo("twomana");
        assertThat(cards.get(2).convertedManaCost().toString()).isEqualTo("threemana");
    }
}