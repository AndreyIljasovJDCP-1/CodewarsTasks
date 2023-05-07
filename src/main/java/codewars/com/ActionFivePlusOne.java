package codewars.com;

public class ActionFivePlusOne {

    public static void main(String[] args) {

        System.out.println(calculateReward(36, 27));
    }

    /**
     * @see <a href="https://www.codewars.com/kata/5976575438480829ff000041">Reward System </a>
     * @param currentPoint bonus
     * @param numberOfJewelry total
     * @return pay, free, bonus
     */
    public static String calculateReward2(int currentPoint, int numberOfJewelry) {
        int pointLeft;
        int freeItem;
        int payItem;
        if (currentPoint / 5 >= numberOfJewelry) {
            return String.format(
                    "Point Left: %d. Free Item: %d. Pay Item: %d",
                    currentPoint - numberOfJewelry * 5, numberOfJewelry, 0);
        } else {
            freeItem = currentPoint / 5;
            payItem = numberOfJewelry - freeItem;
            currentPoint = currentPoint % 5;
            pointLeft = (payItem + currentPoint) % 6;
            payItem = payItem - (payItem + currentPoint) / 6;
            freeItem = numberOfJewelry - payItem;
        }

        return String.format(
                "Point Left: %d. Free Item: %d. Pay Item: %d", pointLeft, freeItem, payItem);
    }

    public static String calculateReward(int n, int m) {
        int r = Math.min((n += m) / 6, m);
        return String.format("Point Left: %d. Free Item: %d. Pay Item: %d", n - r * 6, r, m - r);
    }

    public static String CalculateReward(int points, int jewels) {
        int free = 0, pay = 0;
        while (jewels-- > 0) {
            if (points >= 5) {
                free++;
                points -= 5;
            } else {
                pay++;
                points++;
            }
        }
        return String.format("Point Left: %d. Free Item: %d. Pay Item: %d", points, free, pay);
    }
}
