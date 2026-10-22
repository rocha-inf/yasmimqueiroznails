package com.rocha_inf.yasmimqueiroznails.shared.normalization;


import java.util.Locale;
import java.util.Set;

public final class StringNormalizer {

    private static final Set<String> LOWERCASE_WORDS = Set.of("da", "de", "do", "das", "dos", "e");

    public static String normalize(String string) {
        if (string == null || string.isBlank()) return null;
        return string.strip();
    }

    public static String normalizeName(String name){

        String normalizedName = normalize(name);
        if (normalizedName == null) return null;

        String[] words = normalizedName.toLowerCase(Locale.ROOT).split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i<words.length; i++) {

            if (i > 0) { result.append(' '); }
            String word = words[i];

            if (i > 0 && LOWERCASE_WORDS.contains(word)) { result.append(word); }
            else { result.append(word.substring(0, 1).toUpperCase(Locale.ROOT)).append(word.substring(1)); }

        }

        return result.toString();
    }

}
