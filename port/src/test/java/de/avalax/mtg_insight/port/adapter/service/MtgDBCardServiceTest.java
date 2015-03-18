package de.avalax.mtg_insight.port.adapter.service;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.net.URLEncoder;

import de.avalax.mtg_insight.domain.model.card.Card;

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
    }
}