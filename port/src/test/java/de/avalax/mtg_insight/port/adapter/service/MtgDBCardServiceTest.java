package de.avalax.mtg_insight.port.adapter.service;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.permanent.creature.Creature;
import de.avalax.mtg_insight.domain.model.mana.Mana;
import de.avalax.mtg_insight.domain.model.mana.ManaCost;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created by Melanie on 18.03.2015.
 */
public class MtgDBCardServiceTest {

    private MtgDBCardService mtgDBCardService;

    @Before
    public void setUp() throws Exception {
        mtgDBCardService = new MtgDBCardService(new ManaTokenizer());
    }

    @Test
    public void testCardFromCardname() throws Exception {
        Card card = mtgDBCardService.cardFromCardname("Narset, Enlightened Master");
        assertThat(card, notNullValue());
        assertThat(card.name(), equalTo("Narset, Enlightened Master"));
        assertThat(card.convertedManaCost(), hasSize(6));
        assertThat(card.convertedManaCost().get(0).mana().get(0), equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(1).mana().get(0), equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(2).mana().get(0), equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(3).mana().get(0), equalTo(Mana.BLUE));
        assertThat(card.convertedManaCost().get(4).mana().get(0), equalTo(Mana.RED));
        assertThat(card.convertedManaCost().get(5).mana().get(0), equalTo(Mana.WHITE));
        assertThat(card, instanceOf(Creature.class));
    }

    @Test
    public void testCardFromCardname2() throws Exception {
        Card card = mtgDBCardService.cardFromCardname("Frostburn Weird");
        assertThat(card, notNullValue());
        assertThat(card.name(), equalTo("Frostburn Weird"));
        assertThat(card.convertedManaCost(), hasSize(2));
        assertThat(card.convertedManaCost().get(0).mana().get(0), equalTo(Mana.BLUE));
        assertThat(card.convertedManaCost().get(0).mana().get(1), equalTo(Mana.RED));
        assertThat(card.convertedManaCost().get(1).mana().get(0), equalTo(Mana.BLUE));
        assertThat(card.convertedManaCost().get(1).mana().get(1), equalTo(Mana.RED));
        assertThat(card, instanceOf(Creature.class));
    }

    @Test
    public void testCardFromCardname3() throws Exception {
        Card card = mtgDBCardService.cardFromCardname("Figure of Destiny");
        assertThat(card, notNullValue());
        assertThat(card.name(), equalTo("Figure of Destiny"));
        assertThat(card.convertedManaCost(), hasSize(1));
        assertThat(card.convertedManaCost().get(0).mana().get(0), equalTo(Mana.RED));
        assertThat(card.convertedManaCost().get(0).mana().get(1), equalTo(Mana.WHITE));
        assertThat(card, instanceOf(Creature.class));
    }

    @Test
    public void testCardFromCardname4() throws Exception {
        Card card = mtgDBCardService.cardFromCardname("Brimaz, King of Oreskos");
        assertThat(card, notNullValue());
        assertThat(card.name(), equalTo("Brimaz, King of Oreskos"));
        assertThat(card.convertedManaCost(), hasSize(3));
        assertThat(card.convertedManaCost().get(0).mana().get(0), equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(1).mana().get(0), equalTo(Mana.WHITE));
        assertThat(card.convertedManaCost().get(2).mana().get(0), equalTo(Mana.WHITE));
        assertThat(card, instanceOf(Creature.class));
    }

    @Test
    public void testCardFromCardname5() throws Exception {
        Card card = mtgDBCardService.cardFromCardname("Reaper King");
        assertThat(card, notNullValue());
        assertThat(card.name(), equalTo("Reaper King"));
        assertThat(card.convertedManaCost(), hasSize(5));
        assertThat(card.convertedManaCost().get(0).mana().get(0), equalTo(Mana.HYBRID_TWOCOLORLESS));
        assertThat(card.convertedManaCost().get(0).mana().get(1), equalTo(Mana.WHITE));
        assertThat(card.convertedManaCost().get(1).mana().get(0), equalTo(Mana.HYBRID_TWOCOLORLESS));
        assertThat(card.convertedManaCost().get(1).mana().get(1), equalTo(Mana.BLUE));
        assertThat(card.convertedManaCost().get(2).mana().get(0), equalTo(Mana.HYBRID_TWOCOLORLESS));
        assertThat(card.convertedManaCost().get(2).mana().get(1), equalTo(Mana.BLACK));
        assertThat(card.convertedManaCost().get(3).mana().get(0), equalTo(Mana.HYBRID_TWOCOLORLESS));
        assertThat(card.convertedManaCost().get(3).mana().get(1), equalTo(Mana.RED));
        assertThat(card.convertedManaCost().get(4).mana().get(0), equalTo(Mana.HYBRID_TWOCOLORLESS));
        assertThat(card.convertedManaCost().get(4).mana().get(1), equalTo(Mana.GREEN));
        assertThat(card, instanceOf(Creature.class));
    }
    @Test
    public void testCardFromCardname6() throws Exception {
        Card card = mtgDBCardService.cardFromCardname("Pith Driller");
        assertThat(card, notNullValue());
        assertThat(card.name(), equalTo("Pith Driller"));
        assertThat(card.convertedManaCost(), hasSize(5));
        assertThat(card.convertedManaCost().get(0).mana().get(0), equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(1).mana().get(0), equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(2).mana().get(0), equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(3).mana().get(0), equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(4).mana().get(0), equalTo(Mana.BLACK));
        assertThat(card.convertedManaCost().get(4).mana().get(1), equalTo(Mana.PHYREXIAN));
        assertThat(card, instanceOf(Creature.class));
    }
}