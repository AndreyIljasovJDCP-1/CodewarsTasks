package codewars.com;

import java.util.Arrays;

public class HorseRacing {
    public static int PLACE = 1;
    public static final int UNIT = 10;

    public static void main(String[] args) {

        System.out.println(
                horseRacing(
                        new double[][] {
                                {0.3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, // A
                                {3, 3, 3, 3, 3, 3, 3, 3, 3, 3}, // B
                                {3, 0.1113, 0.1113, 0.1113, 3, 3, 3, 3, 3, 3} // C
                        }));
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5d8de9fdeeae51002600dfa4">Horse Racing</a>
     * @param horses
     * @return
     */
    public static String horseRacing(double[][] horses) {
        int size = horses.length;
        int iterations = horses[0].length;
        boolean finish = false;
        String skip = "";
        int[] status = new int[size];
        double[] race = new double[size];

        for (int i = 0; i < iterations; i++) {
            for (int j = 0; j < size; j++) {
                if (skip.contains(j + "")) continue;
                race[j] += horses[j][i];
                if (race[j] >= UNIT) {
                    finish = true;
                    skip += j;
                    status[j] = PLACE;
                }
            }
            if (finish) {
                PLACE++;
                finish = false;
            }
            if (skip.length() == 3) break;
        }
        System.out.println(java.util.Arrays.toString(race));
        System.out.println(Arrays.toString(status));
        String first = "";
        String second = "";
        String third = "";
        for (int i = 0; i < size; i++) {
            char name = (char) (i + 65);
            if (status[i] == 1) {
                first += " " + name;
            } else if (status[i] == 2) {
                second += " " + name;
            } else if (status[i] == 3) {
                third = "" + name;
            }
        }
        first = first.strip();
        second = second.strip();
        if (first.isEmpty()) return "1st: X 2nd: X 3rd: X";
        if (first.length() > 3) return "1st: " + first + " 2nd: - 3rd: -";
        second = second.isEmpty() ? "X" : second;
        third = first.length() + second.length() > 2 ? "-" : third.isEmpty() ? "X" : third;

        return String.format("1st: %s 2nd: %s 3rd: %s", first, second, third);
    }
}
