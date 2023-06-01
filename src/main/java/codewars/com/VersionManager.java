package codewars.com;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @see <a href="https://www.codewars.com/kata/5bc7bb444be9774f100000c3">Versions manager</a>
 */
public class VersionManager {
  private int major = 0;
  private int minor = 0;
  private int patch = 1;

  private final Deque<int[]> history = new ArrayDeque<>();
  private static final String VERSION_REGEX = "^(\\d+)(?:\\.(\\d+)(?:\\.(\\d+).*)?)?$";

  public VersionManager() {}

  public VersionManager(String version) {
    if (!version.isEmpty()) {
      parseVersionTest(version);
    }
  }

  private void parseVersionTest(String version) {
    Matcher matcher = Pattern.compile(VERSION_REGEX).matcher(version);
    if (version.matches(VERSION_REGEX)) {
      matcher.find();
      String majorGroup = matcher.group(1);
      String minorGroup = matcher.group(2);
      String patchGroup = matcher.group(3);
      major = Integer.parseInt(majorGroup);
      minor = minorGroup == null ? 0 : Integer.parseInt(minorGroup);
      patch = patchGroup == null ? 0 : Integer.parseInt(patchGroup);
    } else {
      throw new IllegalArgumentException("Error occured while parsing version!");
    }
  }

  private void parseVersion(String version) {
    // {MAJOR} or "" parse
    if (version.matches("\\d+")) {
      major = Integer.parseInt(version);
      patch = 0;
    } else if (version.matches("\\d+\\.\\d+")) {
      // {MAJOR}.{MINOR} parse
      String[] versionArray = version.split("\\.");
      major = Integer.parseInt(versionArray[0]);
      minor = Integer.parseInt(versionArray[1]);
      patch = 0;
    } else if (version.matches("\\d+\\.\\d+\\.\\d+.*")) {
      // {MAJOR}.{MINOR}.{PATCH} parse
      String[] versionArray = version.split("\\.");
      major = Integer.parseInt(versionArray[0]);
      minor = Integer.parseInt(versionArray[1]);
      patch = Integer.parseInt(versionArray[2].replaceAll("(\\d+)(.*)", "$1"));
    } else {
      throw new IllegalArgumentException("Error occured while parsing version!");
    }
  }

  public VersionManager major() {
    history.push(getVersion());
    major++;
    minor = 0;
    patch = 0;
    return this;
  }

  public VersionManager minor() {
    history.push(getVersion());
    minor++;
    patch = 0;
    return this;
  }

  public VersionManager patch() {
    history.push(getVersion());
    patch++;
    return this;
  }

  public String release() {
    return String.format("%s.%s.%s", major, minor, patch);
  }

  public VersionManager rollback() {
    if (history.isEmpty()) {
      throw new IllegalStateException("Cannot rollback!");
    } else {
      int[] lastVersionArray = history.pop();
      major = lastVersionArray[0];
      minor = lastVersionArray[1];
      patch = lastVersionArray[2];
    }
    return this;
  }

  private int[] getVersion() {
    return new int[] {major, minor, patch};
  }

  public static void main(String[] args) {
    VersionManager vm = new VersionManager("0125.23.1");
    System.out.println(vm.release());
    vm.minor().patch().patch();
    System.out.println(vm.release());
    vm.major().patch();
    System.out.println(vm.release());
    vm.rollback().rollback();
    System.out.println(vm.release());
    vm.rollback();
    System.out.println(vm.release());
    vm.rollback().rollback();
    System.out.println(vm.release());
    vm.patch();
    System.out.println(vm.release());
  }
}
