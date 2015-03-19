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

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;

/**
 * Created by Melanie on 18.03.2015.
 */
public class MtgDBCardServiceTest {

    private MtgDBCardService mtgDBCardService;
    @Before
    public void setUp() throws Exception {
        mtgDBCardService=new MtgDBCardService();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCardFromCardname() throws Exception {
        Card card = mtgDBCardService.cardFromCardname("Narset, Enlightened Master");
        assertThat(card, Matchers.notNullValue());
        assertThat(card.name(),Matchers.equalTo("Narset, Enlightened Master"));
        assertThat(card.convertedManaCost().get(0).mana().get(0),Matchers.equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(1).mana().get(0),Matchers.equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(2).mana().get(0),Matchers.equalTo(Mana.COLORLESS));
        assertThat(card.convertedManaCost().get(3).mana().get(0),Matchers.equalTo(Mana.BLUE));
        assertThat(card.convertedManaCost().get(4).mana().get(0),Matchers.equalTo(Mana.RED));
        assertThat(card.convertedManaCost().get(5).mana().get(0),Matchers.equalTo(Mana.WHITE));
        assertThat(card,instanceOf(Creature.class));
    }
//    @Test
//    public void testCardFromCardname2() throws Exception {
//        Card card = mtgDBCardService.cardFromCardname("Frostburn Weird");
//        assertThat(card, Matchers.notNullValue());
//        assertThat(card.name(),Matchers.equalTo("Frostburn Weird"));
//        List<Mana> mana1=Arrays.asList(Mana.COLORLESS);
//        List<Mana> mana2=Arrays.asList(Mana.COLORLESS);
//        List<Mana> mana3=Arrays.asList(Mana.COLORLESS);
//        List<Mana> mana4=Arrays.asList(Mana.BLUE);
//        List<Mana> mana5=Arrays.asList(Mana.RED);
//        List<Mana> mana6=Arrays.asList(Mana.WHITE);
//        assertThat(card.convertedManaCost().get(0).mana().get(0),Matchers.equalTo(Mana.COLORLESS));
//        assertThat(card,instanceOf(Creature.class));
//    }
}