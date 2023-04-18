package codewars.com;

public class FizzBuzz {
  public static final int FIRST_DIVIDER = 3;
  public static final int SECOND_DIVIDER = 5;
  public static final int SUM_DIGITS_LIMIT = 10;

  public static void main(String[] args) {

    for (int i = 0; i < 100; i++) {

      if (checkMultiplicity(i, FIRST_DIVIDER) && checkMultiplicity(i, SECOND_DIVIDER)) {
        System.out.println("FizzBuzz");
      } else if (checkMultiplicity(i, FIRST_DIVIDER)) {
        System.out.println("Fizz");
      } else if (checkMultiplicity(i, SECOND_DIVIDER)) {
        System.out.println("Buzz");
      } else {
        System.out.println(i);
      }
    }
  }

  private static boolean checkMultiplicity(int num, int divider) {
    return num % divider == 0;
  }

  private static boolean checkSumDigits(int num) {
    int sumDigits = 0;
    while (num > 0) {
      sumDigits += num % 10;
      num = num / 10;
    }
    return sumDigits < SUM_DIGITS_LIMIT;
  }
}
