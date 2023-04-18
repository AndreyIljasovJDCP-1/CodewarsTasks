package codewars.com;

import java.math.BigInteger;
import java.util.Iterator;

/**
 * @see <a href="https://www.codewars.com/kata/62c30cea4ca944813b2b40e1">
 *     Fibonacci iterator
 *     </a>
 */
public class UnlimitedFibIterator {

    public static void main(String[] args) {

        Iterator<BigInteger> iterator = iterator(0, 1);
        int i = 0;
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            if (i == 10) break;
            i++;
        }
    }

    public static Iterator<BigInteger> iterator(long first, long second) {
        return new Iterator<>() {
            private BigInteger previous = BigInteger.valueOf(second - first);
            private BigInteger current = BigInteger.valueOf(first);

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public BigInteger next() {

                current = current.add(previous);
                previous = current.subtract(previous);
                return previous;
            }
        };
    }
}
