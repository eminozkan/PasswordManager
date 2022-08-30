package kata.primefactor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static kata.primefactor.RomanNumeral.convert;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.of;

/**
 * @author Omer Ozkan
 */
public class RomanNumeralTest {
    // "I" -> 1
    // "V" -> 5
    // "XX" -> 20
    // "Z" -> UnknowmRoman
    // 1 -> "1"
    // 3 -> "Fizz"
    // 6 -> "Fizz"
    // 5 -> "Buzz"
    // 15 -> "FizzBuzz"

    static Stream<Arguments> testData() {
        return Stream.of(
                of("I", 1),
                of("II", 2),
                of("III", 3),
                of("IV", 4),
                of("V", 5),
                of("X", 10),
                of("XI", 11),
                of("XX", 20),
                of("XXV", 25),
                of("MCMXCIX", 1999),
                of("MMXXII", 2022)
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void convertAsExpected(String roman, int expected) {
        assertEquals(expected, convert(roman));
    }

    @Test
    void unknownRoman() {
        final RomanNumeral.UnknownRoman ex = assertThrows(RomanNumeral.UnknownRoman.class, () -> convert("Z"));
        assertThat(ex.getMessage()).contains("Z");
    }


}
