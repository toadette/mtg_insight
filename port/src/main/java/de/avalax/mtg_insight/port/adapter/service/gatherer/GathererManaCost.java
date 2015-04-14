package de.avalax.mtg_insight.port.adapter.service.gatherer;


import java.util.HashMap;

public class GathererManaCost {
    public static final String HYBRID_MANA = ".*(or).*";
    public static final String COLORLESS_MANA = "\\d";
    private HashMap<String, String> matchingMap = new HashMap<>();

    public GathererManaCost() {
        matchingMap.put("Red", "R");
        matchingMap.put("Green", "G");
        matchingMap.put("Black", "B");
        matchingMap.put("Blue", "U");
        matchingMap.put("White", "W");
        matchingMap.put("Phyrexian", "P");
        matchingMap.put("Two", "2");
    }

    public String getManaStringFromGatherer(String gathererMana) {
        if (gathererMana.matches(COLORLESS_MANA)) {
            return gathererMana;
        }
        if (gathererMana.matches(HYBRID_MANA)) {
            return getHybridMana(gathererMana);
        }
        if (gathererMana.startsWith("Phyrexian")) {
            return getPhyrexianMana(gathererMana);
        }
        return matchingMap.get(gathererMana);
    }

    private String getPhyrexianMana(String gathererMana) {
        String[] split = gathererMana.split(" ");
        return "{" + matchingMap.get(split[0]) + matchingMap.get(split[1]) + "}";
    }

    private String getHybridMana(String mana) {
        int index = mana.indexOf("or");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(getMana(mana, 0, index - 1));
        stringBuilder.append("/");
        stringBuilder.append(getMana(mana, index + 2, mana.length()));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private String getMana(String mana, int beginIndex, int endIndex) {
        return matchingMap.get(mana.substring(beginIndex, endIndex).trim());
    }
}
