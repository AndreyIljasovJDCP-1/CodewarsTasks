package codewars.com;

import java.util.Arrays;
import java.util.stream.Stream;

public class Factorial {
    public static void main(String[] args) {
        System.out.println(zeros(10));
    }
    public static int zeros(int n) {
        if (n / 5 == 0) return 0;
        return n / 5 + zeros(n / 5);
    }

    /**
     * print factorial product
     */
    public static void fact() {
        var list =
                Stream.iterate(new long[] {1, 2, 2}, x -> new long[] {x[1], x[1] + 1, x[2] * (x[1] + 1)})
                        .peek(s -> System.out.println(java.util.Arrays.toString(s)))
                        .limit(19)
                        .map(s -> s[0] + " * " + s[1] + " = " + s[2])
                        .toArray(String[]::new);

        for (int i = 1; i < list.length; i++) {
            list[i] = list[i - 1].replaceAll("(= \\d*$)", list[i].substring(list[i].indexOf("*")));
        }

        Arrays.stream(list).forEach(System.out::println);
    }
}
