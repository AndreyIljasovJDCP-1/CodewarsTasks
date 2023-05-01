package codewars.com;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @see <a href="https://www.codewars.com/kata/5659c6d896bc135c4c00021e">
 *     Next smaller number with the same digits
 *     </a>
 *     some tests will include very large numbers.
 */
public class NextSmaller {

    public static void main(String[] args) {
        System.out.println(nextSmaller(2071));
    }


    public static long nextSmaller(long n) {
        var number = Long.toString(n).chars().map(c -> c - 48).boxed().collect(Collectors.toList());
        int size = number.size();
        for (int i = size - 1; i > 0; i--) {
            var prev = number.get(i - 1);
            if (prev > number.get(i)) {
                var begin = new ArrayList<>(number.subList(0, i - 1));
                var end = new ArrayList<>(number.subList(i - 1, size));
                // найти самое близкое число к prev из хвоста
                var offset = new AtomicInteger(1);
                while (true) {
                    var smaller = end.stream().filter(c -> c == prev - offset.get()).findFirst();
                    if (smaller.isPresent()) {
                        end.remove(smaller.get());
                        end.sort(Comparator.reverseOrder());
                        begin.add(smaller.get());
                        begin.addAll(begin.size(), end);
                        if (begin.get(0) == 0) return -1;
                        return Long.parseLong(
                                begin.stream().map(String::valueOf).collect(Collectors.joining()));
                    }
                    offset.incrementAndGet();
                }
            }
        }
        return -1;
    }

    /**
     * @see <a href="https://www.codewars.com/kata/55983863da40caa2c900004e">Next bigger number with
     *     the same digits</a>
     */
    public static long nextBigger(long n) {
        var number = Long.toString(n).chars().map(c -> c - 48).boxed().collect(Collectors.toList());
        int size = number.size();
        for (int i = size - 1; i > 0; i--) {
            var prev = number.get(i - 1);
            if (prev < number.get(i)) {
                var begin = new ArrayList<>(number.subList(0, i - 1));
                var end = new ArrayList<>(number.subList(i, size));
                // найти самое близкое число к текущему из конца
                var offset = new AtomicInteger(1);
                while (true) {
                    var bigger = end.stream().filter(c -> c == prev + offset.get()).findFirst();
                    if (bigger.isPresent()) {
                        end.remove(bigger.get());
                        end.add(prev);
                        end.sort(Comparator.naturalOrder()); // ?
                        begin.add(bigger.get());
                        begin.addAll(begin.size(), end);
                        if (begin.get(0) == 0) return -1; // ?
                        return Long.parseLong(
                                begin.stream().map(String::valueOf).collect(Collectors.joining()));
                    }
                    offset.incrementAndGet();
                }
            }
        }
        return -1;
    }
}
