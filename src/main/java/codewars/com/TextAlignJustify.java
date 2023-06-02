package codewars.com;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://www.codewars.com/kata/537e18b6147aa838f600001b">Text align justify</a>
 */
public class TextAlignJustify {
  public static void main(String[] args) {
    System.out.println(
        justify(
            "Lorem ipsum dolor, sit amet consectetur adipisicing elit."
                + " Unde ipsa aperiam nisi non necessitatibus sunt natus"
                + " magnam id culpa laudantium modi reiciendis corporis"
                + " temporibus sequi, iure optio nobis dolores ipsum.",
            14));
  }

  public static String justify(String text, int width) {
    List<String> result = new ArrayList<>();
    List<String> line = new ArrayList<>();
    String[] words = text.split(" ");
    int i = 0;
    while (i < words.length) {
      int index = i;
      int length = 0;
      line.clear();
      while (index < words.length && (length + 1 + words[index].length()) <= width) {
        line.add(words[index++]);
        length = String.join(" ", line).length();
      }
      result.add(line.size() == 0 ? words[index++] : formLine(line, width));
      i = index;
    }
    result.set(result.size() - 1, result.get(result.size() - 1).replaceAll("\\s+", " "));
    return String.join("\n", result);
  }

  private static String formLine(List<String> line, int width) {
    if (line.size() == 1) return String.join("", line);
    int spaces = width - String.join("", line).length();
    while (spaces > 0) {
      for (int j = 0; j < line.size() - 1; j++) {
        line.set(j, line.get(j) + " ");
        spaces--;
        if (spaces == 0) break;
      }
    }
    return String.join("", line);
  }
}
