package de.avalax.mtg_insight.domain.model.card;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class GenericCardTest {
    @Test
    public void sameCardName_shouldBeEqual() throws Exception {
        GenericCard aCard = new GenericCard("aCard");
        assertThat(aCard).isNotEqualTo(null);
        assertThat(aCard).isNotEqualTo(new GenericCard(null));
        assertThat(aCard).isNotEqualTo(new GenericCard("anotherCard"));
        assertThat(aCard).isEqualTo(aCard);
        assertThat(new GenericCard("sameCard")).isEqualTo(new GenericCard("sameCard"));
    }

    @Test
    public void cardName_shouldBeUsedForHashCode() throws Exception {
        assertThat(new GenericCard("cardName").hashCode()).isEqualTo("cardName".hashCode());
    }

    @Test
    public void toString_shouldReturnCardName() throws Exception {
        assertThat(new GenericCard("cardName").toString()).isEqualTo("GenericCard{name='cardName'}");
    }
}