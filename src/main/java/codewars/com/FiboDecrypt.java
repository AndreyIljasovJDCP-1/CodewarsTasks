package codewars.com;

import java.util.stream.Stream;

/**
 * <a href="https://www.codewars.com/kata/5858fd43c1d5b4399f000138">Fibonacci encryption and
 * decryption </a>
 */
public class FiboDecrypt {
  public static final String alphabet = "abcdefghijklmnopqrstuvwxyz";

  public static void main(String[] args) {
    System.out.println(decrypt("mxucyjiijatffk", "puzzle"));
    System.out.println(crypt("mxucyjiijatffk", "puzzle", false));
    System.out.println(encrypt("codewars", "puzzle"));
    System.out.println(crypt("codewars", "puzzle", true));
  }

    /**
     * vice-versa encrypted/decrypted
   *
   * @param source encrypted/decrypted string
   * @param password password
   * @param crypt encrypt/decrypt
   * @return encrypted/decrypted string
   */
  public static String crypt(String source, String password, boolean crypt) {
    source = crypt ? new StringBuilder(source).reverse() + password : source;
    int offset = sumFiboN(password.length());
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < (crypt ? source.length() : source.length() - password.length()); i++) {
      int next =
          (i & 1) == (crypt ? 0 : 1)
              ? (alphabet.indexOf(source.charAt(i)) + offset % 26) % 26
              : (alphabet.indexOf(source.charAt(i)) + 26 - offset % 26) % 26;
      sb.append(alphabet.charAt(next));
    }
    return crypt ? sb.toString() : sb.reverse().toString();
  }

  public static String encrypt(String source, String password) {
    StringBuilder sb = new StringBuilder(source).reverse();
    String decrypted = sb + password;
    int offset = sumFiboN(password.length());
    sb = new StringBuilder();
    for (int i = 0; i < decrypted.length(); i++) {
      int next =
          (i & 1) == 0
              ? (alphabet.indexOf(decrypted.charAt(i)) + offset % 26) % 26
              : (alphabet.indexOf(decrypted.charAt(i)) + 26 - offset % 26) % 26;
      sb.append(alphabet.charAt(next));
    }
    return sb.toString();
  }

  public static String decrypt(String encrypted, String password) {
    StringBuilder sb = new StringBuilder();
    int offset = sumFiboN(password.length());
    for (int i = 0; i < encrypted.length() - password.length(); i++) {
      int next =
          (i & 1) == 0
              ? (alphabet.indexOf(encrypted.charAt(i)) + 26 - offset % 26) % 26
              : (alphabet.indexOf(encrypted.charAt(i)) + offset % 26) % 26;
      sb.append(alphabet.charAt(next));
    }
    return sb.reverse().toString();
  }

  private static int sumFiboN(int n) {
    int[] fib = new int[] {1, 1, 2};
    return Stream.iterate(new int[] {1, 1}, f -> new int[] {f[1], f[0] + f[1]})
        .limit(n)
        .mapToInt(f -> f[0])
        .sum();
  }
}
