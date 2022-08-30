package kata.primefactor;

import java.util.Map;

/**
 * @author Omer Ozkan
 */
public class RomanNumeral {
    private static final Map<String, Integer> DIGITS = Map.ofEntries(
            Map.entry("I", 1),
            Map.entry("V", 5),
            Map.entry("X", 10),
            Map.entry("C", 100),
            Map.entry("M", 1000)
    );

    public static int convert(String roman) {
        var total = 0;
        while (roman.length() > 1) {
            var current = convertDigit(roman.substring(0, 1));
            var next = convertDigit(roman.substring(1, 2));
            total += current < next ? -current : +current;
            roman = roman.substring(1);
        }
        return convertDigit(roman) + total;
    }

    private static Integer convertDigit(String roman) {
        if (!DIGITS.containsKey(roman)) {
            throw new UnknownRoman("Unknown roman " + roman);
        }
        return DIGITS.get(roman);
    }

    public static class UnknownRoman extends RuntimeException {
        public UnknownRoman(String msg) {
            super(msg);
        }
    }
}
