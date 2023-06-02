package codewars.com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Primes {
    /** проверка на простое число */
    private static boolean isPrimeBoolean(long m) {
        boolean isPrime = true;
        for (int j = 2; j <= Math.floor(Math.sqrt(m)); j++) {
            if ((m % j) == 0) {
                isPrime = false;
                break;
            }
        }
        return isPrime;
    }
    /**решето Эратосфена */
    public static List<Integer> eratosthenesPrime(int max) {
        boolean[] isPrime = new boolean[max];
        Arrays.fill(isPrime, true);

        for (int i = 2; i * i < max; i++) {
            if (isPrime[i]) {
                for (int j = 2 * i; j < max; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i < max; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }
        return primes;
    }

    private static boolean isPrimeOptimized(int number) {
        if (number < 2) {
            return false;
        }
        if (number % 2 == 0) {
            return number == 2;
        }
        if (number % 3 == 0) {
            return number == 3;
        }
        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * @see <a href="https://www.codewars.com/kata/5613d06cee1e7da6d5000055">Steps in Primes</a>
     * @param g step
     * @param m start
     * @param n end
     * @return first pair
     */
    public static long[] step(int g, long m, long n) {
        for (long i = m; i <= n; i++) {
            if (isPrime(i) && isPrime(i + g)) {
                return new long[] {i, i + g};
            }
        }
        return new long[] {};
    }

    private static boolean isPrime(long number) {
        if (number < 2) {
            return false;
        }
        if (number % 2 == 0) {
            return number == 2;
        }
        if (number % 3 == 0) {
            return number == 3;
        }
        for (long i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
}
