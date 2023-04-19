package codewars.com;

import java.util.Comparator;

/**
 * @see <a href="https://www.codewars.com/kata/52b7ed099cdc285c300001cd">
 *     Sum of Intervals
 *     </a>
 */
public class Arrays {

    public static int sumIntervals(int[][] intervals) {
        // Arrays.sort(intervals, Comparator.comparing(a -> a[0]));
        // Arrays.sort(intervals, (a, b) -> Integer.compare(b[1], a[1]));
        Comparator<int[]> comparator =
                (o1, o2) -> {
                    if (o1[0] == o2[0]) {
                        return Integer.compare(o1[1], o2[1]);
                    }
                    return Integer.compare(o1[0], o2[0]);
                };
        var sorted = java.util.Arrays.stream(intervals).sorted(comparator).toArray(int[][]::new);
        System.out.println(java.util.Arrays.deepToString(sorted));

        for (int i = 0; i < sorted.length - 1; i++) {
            if (sorted[i][1] >= sorted[i + 1][0]) {
                if (sorted[i][1] >= sorted[i + 1][1]) {
                    sorted[i + 1][0] = sorted[i][0];
                    sorted[i + 1][1] = sorted[i][1];
                    sorted[i][0] = 0;
                    sorted[i][1] = 0;
                } else {
                    sorted[i + 1][0] = sorted[i][0];
                    sorted[i][0] = 0;
                    sorted[i][1] = 0;
                }
            }
        }
        System.out.println(java.util.Arrays.deepToString(sorted));

        var intervalLength =
                java.util.Arrays.stream(sorted)
                        .filter(s -> !(s[0] == 0 && s[1] == 0))
                        .mapToInt(s -> Math.abs(s[1] - s[0]))
                        .sum();
        System.out.println(intervalLength);
        return -1;
    }
}
