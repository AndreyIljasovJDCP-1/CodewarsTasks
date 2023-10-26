package codewars.com;

import java.util.HashMap;
import java.util.Map;

/**
 * @link <a href="https://www.codewars.com/kata/605da8dc463d940023f2022e">
 *     QR_MessageEncoder</a>
 */
public class QR_MessageEncoder {

    public static final String alphanumeric = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:";
    public static Map<Character, Integer> alphanumericMap;

    static {
        alphanumericMap = new HashMap<>();
        for (int i = 0; i < alphanumeric.length(); i++) {
            alphanumericMap.put(alphanumeric.charAt(i), i);
        }
    }

    public static void main(String[] args) {
        System.out.println(QR_CodesMessageEncoding("HELLO WORLD"));
        System.out.println(QR_CodesMessageEncoding("Hello World"));
        System.out.println(QR_CodesMessageEncoding("9120136"));
    }

    public static String QR_CodesMessageEncoding(String message) {
        Mode mode = checkMode(message);
        return switch (mode) {
            case ALPHANUMERIC -> mode + "=>" + mode.getPrefix() + alphanumericMode(message);
            case BYTE -> mode + "=>" + mode.getPrefix() + byteMode(message);
            case NUMERIC -> mode + "=>" + mode.getPrefix() + numericMode(message);
        };
    }

    private static Mode checkMode(String message) {
        boolean numeric = true;
        for (int i = 0; i < message.length(); i++) {
            char symbol = message.charAt(i);
            if (!Character.isDigit(symbol)) {
                numeric = false;
                if (!alphanumeric.contains(symbol + "")) {
                    return Mode.BYTE;
                }
            }
        }
        return numeric ? Mode.NUMERIC : Mode.ALPHANUMERIC;
    }

    /**
     * The Numeric Mode
     * Prefix Numeric: "0001"
     * Message length. For the numeric mode, this bit string is 10 bits long.
     * Characters: Digits 0-9
     * Encoding: Seperate the message into groups of 3 characters: "1234567" -> "123", "456", "7".
     * For each group, remove the leading zeroes (for example, "034" -> "34").
     * If the group is still three digits long, convert that number into 10 bits.
     * If the group is only two digits long, convert it into 7 bits.
     * Groups with only one remaining digit should be converted into 4 bit.
     * A group containing only 0s should evaluate to "0000".
     * "9120136" (Numeric Mode): prefix is "0001 0000000111", message is "1110010000 0001101 0110"
     */
    private static String numericMode(String message) {

        int length = message.length();
        String messageLen = Integer.toBinaryString(length); // 10 bits длина сообщения
        messageLen = "0".repeat(10 - messageLen.length()) + messageLen + "/";
        //String prefix = "0001" +"/" + messageLen + "/";
        StringBuilder sb = new StringBuilder(messageLen);
        //System.out.println(sb.toString());
        int value = 0;
        int count = 0;
        String group = "";
        String bits;
        for (int i = 0; i < length; i++) {
            group += message.charAt(i) + "";
            count++;
            if (count == 3) {
                group = group.replaceAll("^0+", "");
                if (group.length() == 0) {
                    sb.append("0".repeat(4));
                } else if (group.length() == 1) {
                    bits = Integer.toBinaryString(Integer.parseInt(group));
                    sb.append("0".repeat(4 - bits.length())).append(bits).append("/"); // 4 bits group length 1
                } else if (group.length() == 2) {
                    bits = Integer.toBinaryString(Integer.parseInt(group));
                    sb.append("0".repeat(7 - bits.length())).append(bits).append("/"); // 7 bits group length 2
                } else {
                    bits = Integer.toBinaryString(Integer.parseInt(group));
                    sb.append("0".repeat(10 - bits.length())).append(bits).append("/"); // 10 bits group length 3
                }
                count = 0;
                group = "";
            }
        }
        // System.out.println(sb.toString());
        if (count != 0) {
            group = group.replaceAll("^0+", "");
            if (group.length() == 0) {
                sb.append("0".repeat(4));
            } else if (group.length() == 1) {
                bits = Integer.toBinaryString(Integer.parseInt(group));
                sb.append("0".repeat(4 - bits.length())).append(bits).append("/"); // 4 bits group length 1
            } else if (group.length() == 2) {
                bits = Integer.toBinaryString(Integer.parseInt(group));
                sb.append("0".repeat(7 - bits.length())).append(bits).append("/"); // 7 bits group length 2
            }
        }

        //System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * Byte Mode
     * Prefix Byte: "0100"
     * Message length: 8 bits
     * Encoding: Each character gets encoded into 8 bit by using the char integer value to keep it simple (real QR-Codes use ISO 8859-1 as character set)
     * "Hello World" (Byte Mode): prefix is "0100 00001011",
     * message is "01001000 01100101 01101100 01101100 01101111 00100000 01010111 01101111 01110010 01101100 01100100"
     */
    private static String byteMode(String message) {
        int length = message.length();
        String messageLen = Integer.toBinaryString(length); // 8 bits длина сообщения
        messageLen = "0".repeat(8 - messageLen.length()) + messageLen + "/";
        //String prefix = "0100" +"/" + messageLen + "/";
        StringBuilder sb = new StringBuilder(messageLen);
        char[] intValues = message.toCharArray();
        for (int i = 0; i < length; i++) {
            int value = intValues[i];
            String bits = Integer.toBinaryString(value);
            //System.out.println(bits);
            sb.append("0".repeat(8 - bits.length())).append(bits).append("/"); // 8 bits group length
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * Prefix Alphanumeric: "0010"
     * Message length: 9 bits
     * The Alphanumeric Mode
     * Characters: 45 characters listed at the end of the description (will be provided in the initial solution in the same order as they're listed in the table)
     * Encoding: Seperate the message string into groups of 2 characters: "HELLO WORLD" -> "HE", "LL", "O ", "WO", "RL", "D".
     * For each group, translate the first character to its corresponding int value and multiply it by 45.
     * Than add the int value for the second char to this value.
     * Then convert this value into an 11-bit string. "HE" -> 17*45 + 14 = 779 -> "01100001011".
     * If you encode an odd number of chars, convert the last char to its int value and then to a 6-bit string.
     * "HELLO WORLD" (Alphanumeric Mode): prefix is "0010 000001011", message is "01100001011 01111000110 10001011100 10110111000 10011010100 001101"
     */
    private static String alphanumericMode(String message) {

        int length = message.length();
        String messageLen = Integer.toBinaryString(length); // 9 bits длина сообщения
        messageLen = "0".repeat(9 - messageLen.length()) + messageLen + "/";
        //String prefix = "0010" +"/" + messageLen + "/";
        StringBuilder sb = new StringBuilder(messageLen);
        //System.out.println(sb.toString());
        boolean oddLen = (length & 1) == 1; // 6 bits string last character
        int value = 0;
        for (int i = 0; i < (oddLen ? length - 1 : length); i++) {
            if ((i & 1) == 0) {
                //value = alphanumericMap.get(message.charAt(i)) * 45;
                value = alphanumeric.indexOf(message.charAt(i)) * 45;
            } else {
                //value += alphanumericMap.get(message.charAt(i));
                value += alphanumeric.indexOf(message.charAt(i));
                String bits = Integer.toBinaryString(value);
                //System.out.println(bits);
                sb.append("0".repeat(11 - bits.length())).append(bits).append("/"); // 11 bits group length
            }
            //System.out.println(sb.toString());
        }
        if (oddLen) {
            //String bits = Integer.toBinaryString(alphanumericMap.get(message.charAt(length - 1)));
            String bits = Integer.toBinaryString(alphanumeric.indexOf(message.charAt(length - 1)));
            //System.out.println(bits);
            sb.append("0".repeat(6 - bits.length())).append(bits);
        }

        //System.out.println(sb.toString());
        return sb.toString();
    }

    public enum Mode {
        ALPHANUMERIC("0010"), BYTE("0100"), NUMERIC("0001");
        private final String prefix;

        Mode(String prefix) {
            this.prefix = prefix;
        }

        public String getPrefix() {
            return prefix;
        }

        @Override
        public String toString() {
            return this.name().charAt(0) + this.name().substring(1).toLowerCase() + " Mode";
        }

    }

}
