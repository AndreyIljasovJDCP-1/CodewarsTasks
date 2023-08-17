package codewars.com;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ArraysSol {

    public static Deque<Integer> deque = new ArrayDeque<>();

    public static void main(String[] args) {
        System.out.println(rangeExtraction(new int[] {-3, -2, -1, 2, 10, 15, 16, 18, 19, 20}));
    }

    /**
     * @see <a href="https://www.codewars.com/kata/51ba717bb08c1cd60f00002f">Range Extraction</a>
     * @param arr
     * @return
     */
    public static String rangeExtraction(int[] arr) {
        if (arr.length < 3)
            return Arrays.stream(arr).mapToObj(String::valueOf).collect(Collectors.joining());
        int i = 0;
        List<String> res = new ArrayList<>();
        while (i < arr.length) {
            int length = 0;
            while (i + length + 1 < arr.length && arr[i + length] + 1 == arr[i + 1 + length]) {
                length++;
            }
            if (length > 1) {
                res.add(arr[i] + "-" + arr[length + i]);
                i += length;
            } else {
                res.add(arr[i] + "");
            }
            i++;
        }
        System.out.println(String.join(",", res));
        return String.join(",", res);
    }

    /**
     * @see <a href="https://www.codewars.com/kata/55e86e212fce2aae75000060">Integers: Recreation Two
     *     </a>
     */
    public static List<long[]> prod2Sum(long a, long b, long c, long d) {
        // a, b, c, d. Например, 2 — это bc — ad, 263 — это ac + bd, 23 — это bd — ac, 262 — это ad + bc
        // [103, 242], [122, 233]построены из a, b, c, d?
        // На мой взгляд, разложение должно исходить из чисел a, b, c, d.
        // Например, 2 — это bc — ad, 263 — это ac + bd, 23 — это bd — ac, 262 — это ad + bc.
        // Все это получается из разложения (axa + bxb)x(cxc + dxd)?
        // Или вы сделали прямое разложение квадратного произведения (axa + bxb)x(cxc + dxd)
        // , как это было сделано в одном из моих других ката?

        long multiSquares = (a * a + b * b) * (c * c + d * d);
        Set<Long> ranges = new TreeSet<>();
        ranges.add(a * d + b * c);
        ranges.add(a * c + b * d);

        ranges.add(Math.abs(a * d - b * c));
        ranges.add(Math.abs(b * c - a * d));

        ranges.add(Math.abs(a * c - b * d));
        ranges.add(Math.abs(b * d - a * c));

        System.out.println(multiSquares);
        System.out.println(ranges);
        if (multiSquares % 4 == 3) return List.of(new long[] {});
        List<long[]> result = new ArrayList<>();
        for (Long square : ranges) {
            var remainder = (long) Math.sqrt(multiSquares - square * square);
            if (ranges.contains(remainder) && square <= remainder) {
                result.add(new long[] {square, remainder});
            }
        }
        result.forEach(s -> System.out.println(Arrays.toString(s)));
        return result;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/52b7ed099cdc285c300001cd">
     *     Sum of Intervals
     *     </a>
     */
    public static int sumIntervals(int[][] intervals) {
        // ArraysSol.sort(intervals, Comparator.comparing(a -> a[0]));
        // ArraysSol.sort(intervals, (a, b) -> Integer.compare(b[1], a[1]));
        Comparator<int[]> comparator =
                (o1, o2) -> {
                    if (o1[0] == o2[0]) {
                        return Integer.compare(o1[1], o2[1]);
                    }
                    return Integer.compare(o1[0], o2[0]);
                };
        var sorted = Arrays.stream(intervals).sorted(comparator).toArray(int[][]::new);
        System.out.println(Arrays.deepToString(sorted));

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
        System.out.println(Arrays.deepToString(sorted));

        var intervalLength =
                Arrays.stream(sorted)
                        .filter(s -> !(s[0] == 0 && s[1] == 0))
                        .mapToInt(s -> Math.abs(s[1] - s[0]))
                        .sum();
        System.out.println(intervalLength);
        return -1;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/51edd51599a189fe7f000015">Mean Square Error </a>
     * @param arr1
     * @param arr2
     * @return average diff^2
     */
    public static double solution(int[] arr1, int[] arr2) {

        return 1.0
                * IntStream.range(0, arr1.length).map(i -> (arr1[i] - arr2[i]) * (arr1[i] - arr2[i])).sum()
                / arr1.length;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5526fc09a1bbd946250002dc">Find The Parity Outlier
     *     </a>
     */
    static int find(int[] integers) {
        boolean find = Arrays.stream(integers).map(x -> x & 1).limit(3).sum() < 2;
        return Arrays.stream(integers).filter(x -> (x & 1) == (find ? 1 : 0)).findFirst().getAsInt();
    }

    /**
     * @see <a href="https://www.codewars.com/kata/54d7660d2daf68c619000d95">Common Denominators</a>
     * @param lst
     * @return
     */
    public static String convertFractions(long[][] lst) {
        if (lst.length == 0) return "";
        simplify(lst);
        System.out.println(Arrays.deepToString(lst));
        var max = Arrays.stream(lst).mapToLong(x -> x[1]).max().getAsLong();
        int i = 1;
        while (true) {
            boolean flag = true;
            for (long[] fractions : lst) {
                flag &= max * i % fractions[1] == 0;
            }
            if (flag) break;
            i++;
        }

        long denominator = i * max;
        System.out.println(denominator + " - общий знаменатель");
        return Arrays.stream(lst)
                .map(
                        x -> String.format("(%d,%d)", x[0] * (denominator / x[1]), x[1] * (denominator / x[1])))
                .collect(Collectors.joining());
    }

    private static void simplify(long[][] list) {

        for (long[] fraction : list) {
            for (long j = fraction[0]; j > 1; j--) {
                if (fraction[1] % j == 0 && fraction[0] % j == 0) {
                    fraction[0] = fraction[0] / j;
                    fraction[1] = fraction[1] / j;
                    break;
                }
            }
        }
    }

    /**
     * @see <a href="https://www.codewars.com/kata/521c2db8ddc89b9b7a0000c1">Snail Sort</a>
     * @param array nxn
     * @return array in a clockwise snail-shell pattern.
     */
    public static int[] snail(int[][] array) {
        if (array.length == 0 || array[0].length == 0) return new int[] {};
        int n = array.length;
        int[] result = new int[n * n];
        int index = 0;
        int up = 0;
        int down = n - 1;
        int left = 0;
        int right = n - 1;
        while (index < n * n) {
            for (int i = up; i <= right; i++) result[index++] = array[up][i];
            up++;
            for (int i = up; i <= down; i++) result[index++] = array[i][right];
            right--;
            for (int i = right; i >= left; i--) result[index++] = array[down][i];
            down--;
            for (int i = down; i >= up; i--) result[index++] = array[i][left];
            left++;
        }
        return result;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5270d0d18625160ada0000e4">Greed is Good</a>
     * @param dice five six-sided dice
     * @return sum points
     */
    public static int greedyBest(int[] dice) {
        int n[] = new int[7];
        for (int d : dice) n[d]++;
        return n[1] / 3 * 1000
                + n[1] % 3 * 100
                + n[2] / 3 * 200
                + n[3] / 3 * 300
                + n[4] / 3 * 400
                + n[5] / 3 * 500
                + n[5] % 3 * 50
                + n[6] / 3 * 600;
    }

    public static int greedy(int[] dice) {
        var map =
                Arrays.stream(dice)
                        .boxed()
                        .collect(Collectors.toMap(Function.identity(), s -> 1, Integer::sum));
        int res = 0;
        for (Map.Entry<Integer, Integer> kv : map.entrySet()) {
            int key = kv.getKey();
            int value = kv.getValue();
            if (key == 1) {
                res += value / 3 * 1000 + value % 3 * 100;
            } else if (key == 5) {
                res += value / 3 * 500 + value % 3 * 50;
            } else {
                res += value / 3 * key * 100;
            }
        }
        return res;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/57b6f5aadb5b3d0ae3000611">Length of missing
     *     array</a>
     */
    public static int getLengthOfMissingArray(Object[][] arrayOfArrays) {
        if (arrayOfArrays == null || arrayOfArrays.length == 0) return 0;
        if (Arrays.stream(arrayOfArrays).anyMatch(a -> a == null || a.length == 0)) return 0;
        var sorted = Arrays.stream(arrayOfArrays).mapToInt(a -> a.length).sorted().toArray();
        for (int i = 0; i < sorted.length - 1; i++) {
            if (sorted[i] != sorted[i + 1] - 1) return sorted[i] + 1;
        }
        return 0;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5ce399e0047a45001c853c2b">
     *     Sums of Parts of array</a>
     * @param ls array
     * @return sum parts
     */
    public static int[] sumParts(int[] ls) {
        int[] res = new int[ls.length + 1];
        int sum = Arrays.stream(ls).sum();
        for (int i = 0; i < ls.length; i++) {
            res[i] = sum;
            sum -= ls[i];
        }
        return res;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/514b92a657cdc65150000006">
     * Multiples of 3 or 5</a>
     * @param number input
     * @return sum
     */
    public static int solution(int number) {
        if (number < 1) return 0;
        return IntStream.range(3, number).filter(x -> multiFive(x) || multiThree(x)).sum();
    }

    private static boolean multiThree(int number) {
        return Integer.toString(number).chars().map(c -> c - 48).sum() % 3 == 0;
    }

    private static boolean multiFive(int number) {
        return (number % 10) == 0 || (number % 10) == 5;
    }

    /**
     * Table of Multiplication
     * @param n
     * @return
     */
    public static int[][] table(int n) {
        int[][] arr = new int[n][n];
        IntStream.rangeClosed(1, n)
                .forEach(i -> arr[i - 1] = IntStream.rangeClosed(1, n).map(el -> el * i).toArray());
        for (int[] lines : arr) {
            for (int element : lines) {
                System.out.print(element);
            }
            System.out.println();
        }
        return arr;
    }

    public static int[][] multiplicationTable(int n) {

        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> IntStream.rangeClosed(1, n).map(j -> i * j).toArray())
                .toArray(int[][]::new);
    }

    /**
     * @see <a href="https://www.codewars.com/kata/645fb55ecf8c290031b779ef">Latin Squares</a>
     * @param n
     * @return
     */
    public static int[][] makeLatinSquare(int n) {
        final int[][] latinSquare = new int[n][n];

        IntStream.rangeClosed(1, n).forEach(deque::offerLast);
        latinSquare[0] = deque.stream().mapToInt(Integer::intValue).toArray();
        IntStream.range(1, n).forEach(i -> latinSquare[i] = generateLine());
        print(latinSquare);
        return latinSquare;
    }

    public static int[][] makeLatinSquareBest(int n) {
        return IntStream.range(0, n)
                .mapToObj(i -> IntStream.range(0, n).map(j -> (j + i) % n + 1).toArray())
                .toArray(int[][]::new);
    }

    private static int[] generateLine() {
        deque.offerLast(deque.pollFirst());
        return deque.stream().mapToInt(Integer::intValue).toArray();
    }

    private static void print(int[][] arr) {
        for (int[] lines : arr) {
            for (int el : lines) {
                System.out.print(el + " ");
            }
            System.out.println();
        }
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5a6c4086373c2e2a07000075">Invert Array Middle</a>
     * @param n
     * @return
     */
    public static int[][] invertArrayMiddle(int n) {
        int[][] result = new int[n][n];
        int k = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = k++;
            }
        }
        k = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 || i == n - 1 || k % n == 1 || k % n == 0) {
                    result[j][i] = k;
                }
                k++;
            }
        }
        for (int[] lines : result) {
            for (int el : lines) {
                System.out.print(el + " ");
            }
            System.out.println();
        }
        return result;
    }

    /**
     * SWAP with XOR void если в том же массиве
     *
     * @param arr массив
     * @param indexA 1-й элемент
     * @param indexB 2-й элемент
     */
    public static int[] swap(int[] arr, int indexA, int indexB) {
        arr[indexA] ^= arr[indexB];
        System.out.println(Arrays.toString(arr));
        arr[indexB] ^= arr[indexA];
        System.out.println(Arrays.toString(arr));
        arr[indexA] ^= arr[indexB];
        System.out.println(Arrays.toString(arr));
        return arr;
    }
}
