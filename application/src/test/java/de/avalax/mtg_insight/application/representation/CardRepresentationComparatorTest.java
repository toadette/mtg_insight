package de.avalax.mtg_insight.application.representation;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.CardBuilder;
import de.avalax.mtg_insight.domain.model.mana.ConvertedManaCost;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

import static org.assertj.core.api.Assertions.assertThat;

public class CardRepresentationComparatorTest {
    private ConvertedManaCost convertedManaCost(String cmc, Mana... manaa) {
        List<ManaCost> manaCostList = new ArrayList<>();
        for (Mana mana : manaa) {
            List<Mana> manaCost = new ArrayList<>();
            manaCost.add(mana);
            manaCostList.add(new ManaCost(manaCost, ""));
        }
        return new ConvertedManaCost(cmc, manaCostList);
    }

    private CardRepresentationComparator cardRepresentationComparator;

    private List<CardRepresentation> cardRepresentations;

    @Before
    public void setUp() throws Exception {
        cardRepresentations = new ArrayList<>();
        cardRepresentationComparator = new CardRepresentationComparator();
    }

    @Test
    public void sortEmptyList() throws Exception {
        Collections.sort(cardRepresentations, cardRepresentationComparator);

        assertThat(cardRepresentations).isEmpty();
    }

    @Test
    public void sortSortedList() throws Exception {
        cardRepresentations.add(new CardRepresentation(new CardBuilder("a").build()));
        cardRepresentations.add(new CardRepresentation(new CardBuilder("b").build()));

        Collections.sort(cardRepresentations, cardRepresentationComparator);

        assertThat(cardRepresentations.get(0).name()).isEqualTo("a");
        assertThat(cardRepresentations.get(1).name()).isEqualTo("b");
    }

    @Test
    public void sortByName() throws Exception {
        cardRepresentations.add(new CardRepresentation(new CardBuilder("a").build()));
        cardRepresentations.add(new CardRepresentation(new CardBuilder("c").build()));
        cardRepresentations.add(new CardRepresentation(new CardBuilder("b").build()));

        Collections.sort(cardRepresentations, cardRepresentationComparator);

        assertThat(cardRepresentations.get(0).name()).isEqualTo("a");
        assertThat(cardRepresentations.get(1).name()).isEqualTo("b");
        assertThat(cardRepresentations.get(2).name()).isEqualTo("c");
    }

    @Test
    public void sortByMana() throws Exception {
        cardRepresentations.add(new CardRepresentation(new CardBuilder("").convertedManaCost(convertedManaCost("onemana", Mana.BLACK)).build()));
        cardRepresentations.add(new CardRepresentation(new CardBuilder("").convertedManaCost(convertedManaCost("threemana", Mana.GREEN, Mana.BLACK, Mana.RED)).build()));
        cardRepresentations.add(new CardRepresentation(new CardBuilder("").convertedManaCost(convertedManaCost("twomana", Mana.BLACK, Mana.BLACK)).build()));

        Collections.sort(cardRepresentations, cardRepresentationComparator);

        assertThat(cardRepresentations.get(0).convertedManaCost()).isEqualTo("onemana");
        assertThat(cardRepresentations.get(1).convertedManaCost()).isEqualTo("twomana");
        assertThat(cardRepresentations.get(2).convertedManaCost()).isEqualTo("threemana");
    }
}