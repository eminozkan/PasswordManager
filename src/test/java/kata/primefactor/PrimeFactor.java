package kata.primefactor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Omer Ozkan
 */
public class PrimeFactor {
    public static List<Integer> of(int n) {
        var primes = new ArrayList<Integer>();


        for(int divisor = 2; n > 1; divisor++) {
            for (;n % divisor == 0; n/= divisor) {
                primes.add(divisor);
            }
        }

        return primes;
    }
}
