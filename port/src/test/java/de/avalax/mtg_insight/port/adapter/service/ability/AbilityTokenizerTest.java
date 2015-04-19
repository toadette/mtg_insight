package de.avalax.mtg_insight.port.adapter.service.ability;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.avalax.mtg_insight.domain.model.card.Ability;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by Melanie on 06.04.2015.
 */
public class AbilityTokenizerTest {

    private String description;

    public void testCase1() throws Exception {
        loadTestdataFromResource(getClass().getResourceAsStream("1_testcase.properties"));
    }

    private void loadTestdataFromResource(InputStream resourceAsStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
        String line = br.readLine();
        for (int cnt = 0; line != null; cnt++, line = br.readLine()) {
            if (cnt > 3) {
                description += line;
            }
        }
    }

    @Test
    public void getAbilitiesFromDescription_ShouldReturnEmptyList() throws Exception {
        testCase1();
        AbilityTokenizer abilityTokenizer = new AbilityTokenizer();
        List<Ability> abilitiesFromDescription = abilityTokenizer.getAbilitiesFromDescription("");
        assertThat(abilitiesFromDescription, hasSize(0));
        System.out.println(description);
    }
}
