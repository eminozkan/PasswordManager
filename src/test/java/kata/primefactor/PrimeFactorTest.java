package kata.primefactor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Omer Ozkan
 */
public class PrimeFactorTest {

    @Test
    void one() {
        assertThat(PrimeFactor.of(1)).isEqualTo(List.of());
    }

    @Test
    void two() {
        assertThat(PrimeFactor.of(2)).isEqualTo(List.of(2));
    }

    @Test
    void three() {
        assertThat(PrimeFactor.of(3)).isEqualTo(List.of(3));
    }

    @Test
    void four() {
        assertThat(PrimeFactor.of(4)).isEqualTo(List.of(2, 2));
    }

    @Test
    void five() {
        assertThat(PrimeFactor.of(5)).isEqualTo(List.of(5));
    }

    @Test
    void six() {
        assertThat(PrimeFactor.of(6)).isEqualTo(List.of(2, 3));
    }

    @Test
    void seven() {
        assertThat(PrimeFactor.of(7)).isEqualTo(List.of(7));
    }

    @Test
    void eight() {
        assertThat(PrimeFactor.of(8)).isEqualTo(List.of(2, 2, 2));
    }

    @Test
    void nine() {
        assertThat(PrimeFactor.of(9)).isEqualTo(List.of(3, 3));
    }

    @Test
    void ten() {
        assertThat(PrimeFactor.of(10)).isEqualTo(List.of(2, 5));
    }

    @Test
    void different() {
        assertThat(PrimeFactor.of(2 * 2 * 5 * 5 * 7 * 11 * 13 * 31)).isEqualTo(List.of(2, 2, 5, 5, 7, 11, 13, 31));
    }
}
