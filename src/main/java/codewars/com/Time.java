package codewars.com;

import java.util.ArrayList;
import java.util.List;


public class Time {
    public static void main(String[] args) {
        System.out.println(formatDuration(3601));
    }

    /**
     * @see <a href="https://www.codewars.com/kata/52742f58faf5485cae000b9a">
     *     Human readable duration format
     *     </a>
     */
    public static String formatDuration(int seconds) {
        if (seconds == 0) return "now";

        String[] timeUnit = new String[] {"year", "day", "hour", "minute", "second"};

        int[] timeValue =
                new int[] {
                        seconds / (3600 * 24 * 365),
                        seconds / (3600 * 24) % 365,
                        seconds / 3600 % 24,
                        seconds / 60 % 60,
                        seconds % 60
                };
        List<String> result = new ArrayList<>();

        for (int i = 0; i < timeValue.length; i++) {

            if (timeValue[i] == 0) {
                continue;
            }
            if (timeValue[i] == 1) {
                result.add("1 " + timeUnit[i]);
            } else {
                result.add(timeValue[i] + " " + timeUnit[i] + "s");
            }
        }
        for (int i = 0; i < result.size(); i++) {
            if (i < result.size() - 2) {
                result.set(i, result.get(i) + ", ");
            } else if (i == result.size() - 2) {
                result.set(i, result.get(i) + " and ");
            }
        }
        return String.join("", result);
    }

    public static int day(double content, double percent, double limit, double fullContent) {
        if (content <= fullContent * limit / 100) return 0;
        return 1 + day(content * (1 - percent / 100), percent, limit, fullContent);
    }
    /*String dateAndTime =
    new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime());
System.out.println(dateAndTime);*/
}
