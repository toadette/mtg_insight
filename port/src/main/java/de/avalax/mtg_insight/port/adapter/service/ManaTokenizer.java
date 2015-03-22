package de.avalax.mtg_insight.port.adapter.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManaTokenizer {

    private static final String HYBRID_MANA = "^\\{[A-Z2]/[A-Z2]\\}.*$";
    private static final String PHYREXIAN_MANA = "^\\{([A-Z]P)\\}.*";
    private static Pattern hybrid_Mana_Pattern = Pattern.compile("^\\{([A-Z2]/[A-Z2])\\}");
    private static Pattern PHYREXIAN_MANA_PATTERN=Pattern.compile("^\\{([A-Z]P)\\}");

    public List<ManaCostToken> get(String manaCostString) {
        if (manaCostString == null || manaCostString.isEmpty()) {
            return Collections.emptyList();
        }
        List<ManaCostToken> manaCostTokens = new ArrayList<>();
        for (int position = 0; position < manaCostString.length(); position++) {
            if (manaCostString.substring(position).matches(HYBRID_MANA)) {
                Matcher matcher = hybrid_Mana_Pattern.matcher(manaCostString.substring(position));
                if (matcher.find()) {
                    addManaCostToken(manaCostTokens, matcher.group(1));
                    position += manaCostString.substring(position + 1).indexOf("}") + 1;
                }
            } else if (manaCostString.substring(position).matches(PHYREXIAN_MANA)) {
                Matcher matcher= PHYREXIAN_MANA_PATTERN.matcher(manaCostString.substring(position));
                if(matcher.find()) {
                    addManaCostToken(manaCostTokens, matcher.group(1));
                    position += manaCostString.substring(position + 1).indexOf("}") + 1;
                }
            } else {
                addManaCostToken(manaCostTokens, String.valueOf(manaCostString.charAt(position)));
            }
        }
        return Collections.unmodifiableList(manaCostTokens);
    }

    private void addManaCostToken(List<ManaCostToken> manaCostTokens, String manaString) {
        manaCostTokens.add(new ManaCostToken(manaString));
    }
}
