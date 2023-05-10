package codewars.com;

import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://www.codewars.com/kata/5ca24526b534ce0018a137b5">
 *     Texting with an old-school mobile phone
 *     </a>
 *     sendMessage("Def Con 1!") => "#3#33 3330#222#666 6601-1111"
 */
public class OldMobileText {

    public static void main(String[] args) {
        System.out.println(textOldMobile("Def Con 1!"));
    }
    public static String textOldMobile(String message) {

        Map<Character, String> keyboard = new HashMap<>();

        // fill  letters
        int button = 2;
        int count = 1;
        for (int i = 1; i < 28; i++) {
            keyboard.put((char) (i + 96), (button + "").repeat(count++));
            if (i == 18 || i == 25) continue;
            if (count >= 4) {
                count = 1;
                button++;
            }
        }
        // fill digits
        for (int i = 0; i < 10; i++) {
            keyboard.put((char) (i + 48), i + "-");
        }
        // fill special symbols
        keyboard.put('.', "1");
        keyboard.put(',', "11");
        keyboard.put('?', "111");
        keyboard.put('!', "1111");
        keyboard.put('*', "*-");
        keyboard.put('\'', "*");
        keyboard.put('-', "**");
        keyboard.put('+', "***");
        keyboard.put('=', "****");
        keyboard.put(' ', "0");
        keyboard.put('#', "#-");

        keyboard.entrySet().forEach(System.out::println);

        char[] input = message.toCharArray();
        String output = "";
        boolean uCase = false;
        char currentChar;
        char nextChar;
        for (int i = 0; i < input.length; i++) {
            currentChar = input[i];
            nextChar = i < input.length - 1 ? input[i + 1] : '#';

            if (Character.isLetter(currentChar)) {
                if (Character.isUpperCase(currentChar) ^ uCase) {
                    uCase = !uCase;
                    output += "#";
                }
            }

      /*if (Character.isUpperCase(currentChar) && !uCase) {
        uCase = true;
        output += "#";
      } else if (Character.isLowerCase(currentChar) && uCase) {
        uCase = false;
        output += "#";
      }*/

            output += keyboard.get(Character.toLowerCase(currentChar));

            char currentButton = keyboard.get(Character.toLowerCase(currentChar)).charAt(0);
            char nextButton = keyboard.get(Character.toLowerCase(nextChar)).charAt(0);

            if (currentButton == nextButton) {
                if (output.charAt(output.length() - 1) == '-') {
                    output += "";
                } else if (Character.isLetter(nextChar)) {
                    output += uCase == Character.isUpperCase(nextChar) ? " " : "";
                } else {
                    output += " ";
                }
            }
        }
        return output;
    }
}
