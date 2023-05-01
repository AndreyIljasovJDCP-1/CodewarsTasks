package codewars.com;

/**
 * @see <a href="https://www.codewars.com/kata/54eb33e5bc1a25440d000891">Square into Squares. Protect trees!</a>
 */
public class SaveTrees {

    public static void main(String[] args) {
        System.out.println(decomposeBest(50));
    }

    /**
     * Best solution
     */
    public static String decomposeBest(long n) {
        return decomposed(n, n * n);
    }

    public static String decomposed(long n, long sum) {
        for (long i = n - 1; i >= 1; i--) {
            long remainder = sum - i * i;
            if (remainder == 0) {
                return "" + i;
            }
            if (remainder > 0) {
                String res = decomposed(i, remainder);
                if (res != null) {
                    return res + " " + i;
                }
            }
        }
        return null;
    }
}
