package de.avalax.mtg_insight.port.adapter.service.gatherer;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PropertyReader {
    public CardProperties loadCardPropertiesFromResource(String resource) throws Exception {
        CardProperties cardProperties = new CardProperties();

        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(resource)));
        String line = br.readLine();
        for (int cnt = 0; line != null; cnt++, line = br.readLine()) {
            if (cnt == 0) {
                cardProperties.name = line;
            } else if (cnt == 1) {
                cardProperties.type = line;
            } else if (cnt == 2) {
                Collections.addAll(cardProperties.manaList, line.split(";"));
            } else if (cnt == 3) {
                cardProperties.powerToughness = line;
            } else {
                cardProperties.description += line;
            }
        }
        return cardProperties;
    }

    public static class CardProperties {
        public CardProperties() {
            this.manaList = new ArrayList<>();
        }

        public List<String> manaList;
        public String name;
        public String type;
        public String powerToughness;
        public String description;
    }
}
