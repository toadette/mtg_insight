package de.avalax.mtg_insight.port.adapter.service.ability;

import org.junit.Test;

import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Ability;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by Melanie on 06.04.2015.
 */
public class AbilityTokenizerTest {

    @Test
    public void getAbilitiesFromDescription_ShouldReturnEmptyList() throws Exception {
        de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer abilityTokenizer=new de.avalax.mtg_insight.port.adapter.service.ability.AbilityTokenizer();
        List<Ability> abilitiesFromDescription = abilityTokenizer.getAbilitiesFromDescription("");
        assertThat(abilitiesFromDescription,hasSize(0));

    }
}