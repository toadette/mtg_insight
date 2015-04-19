package de.avalax.mtg_insight.port.adapter.service.card;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.avalax.mtg_insight.domain.model.card.Card;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TranslatingCardServiceTest {
    @InjectMocks
    private TranslatingCardService translatingCardService;
    @Mock
    private JsonInCardAdapter jsonInCardAdapter;
    @Mock
    private CardInJsonAdapter cardInJsonAdapter;
    @Mock
    private Card card;

    @Test
    public void aJsonString_shouldReturnACard() throws Exception {
        String aJsonString = "json";
        when(jsonInCardAdapter.createFromJson(aJsonString)).thenReturn(card);

        Card cardFromJson = translatingCardService.cardFromJson(aJsonString);

        assertThat(cardFromJson, equalTo(cardFromJson));
    }

    @Test
    public void aCard_shouldReturnJson() throws Exception {
        String aJsonString = "json";
        when(cardInJsonAdapter.fromCard(card)).thenReturn(aJsonString);

        String json = translatingCardService.jsonFromCard(card);

        assertThat(json, equalTo(aJsonString));
    }
}