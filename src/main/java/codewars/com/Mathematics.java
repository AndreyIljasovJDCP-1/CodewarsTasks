package codewars.com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mathematics {

    /**
     * @see <a href="https://www.codewars.com/kata/5547cc7dcad755e480000004">Is my friend
     *     cheating?</a>
     * @param n 1+2+3+...n
     * @return [a,b] a*b=sumN - (a+b)
     */
    public static List<long[]> removNb(long n) {
        List<long[]> result = new ArrayList<>();
        long sum = n * (n + 1) / 2;
        double c = (sum - n * 2.0) / n;
        long xMin = (long) Math.ceil(c);
        for (long i = xMin; i <= n; i++) {
            double y = (sum - i) / (i + 1.0);
            if (y == i) continue;
            if (y % 1 == 0) {
                result.add(new long[] {i, (long) y});
            }
        }
        result.forEach(s -> System.out.println(Arrays.toString(s)));
        return result;
    }
}
