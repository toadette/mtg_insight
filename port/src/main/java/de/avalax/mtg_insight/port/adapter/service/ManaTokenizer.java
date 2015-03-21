package de.avalax.mtg_insight.port.adapter.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManaTokenizer {

    private static final String HYBRID_MANA = "^\\{.*\\}.*$";
    private static Pattern pattern=Pattern.compile("^\\{([A-Z2]/[A-Z2])\\}");
    public List<ManaCostToken> get(String manaCostString) {
        if (manaCostString == null || manaCostString.isEmpty()) {
            return Collections.emptyList();
        }
        List<ManaCostToken> manaCostTokens = new ArrayList<>();
        for (int position = 0; position < manaCostString.length(); position++) {
            if (manaCostString.substring(position).matches(HYBRID_MANA)) {
                Matcher matcher = pattern.matcher(manaCostString.substring(position));
                if(matcher.find()){
                    addManaCostToken(manaCostTokens, matcher.group(1));
                    position += manaCostString.substring(position+1).indexOf("}")+1;
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
