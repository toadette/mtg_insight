package de.avalax.mtg_insight.port.adapter.service.gatherer;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import de.avalax.mtg_insight.domain.model.card.Card;
import de.avalax.mtg_insight.domain.model.card.Creature;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class GathererHelperTest {
    String type;
    String name;
    String description;
    String mana;

    public void testCase1() throws Exception {
        InputStream resourceAsStream = getClass().getResourceAsStream("1_testcase.properties");
        InputStreamReader in = new InputStreamReader(resourceAsStream);
        BufferedReader br = new BufferedReader(in);
        String read = br.readLine();
        int cnt=0;
        while(read != null) {
            if(cnt==0){
                name=read;
            }else if(cnt==1){
                type=read;
            }else if(cnt==2){
               mana=read;
            }else{
                description+=read;
            }
            ++cnt;
            read=br.readLine();
        }
    }
    @Test
    public void getCard_shouldReturnNotNull() throws Exception {
        testCase1();
        GathererHelper gathererHelper=new GathererHelper(null,null,null);
        Card card = gathererHelper.getCard(type, name, "description", new ArrayList<String>());
        assertThat(card.name(), equalTo("Narset, Enlightened Master"));
        assertThat(card,instanceOf(Creature.class));

    }
}