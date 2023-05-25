package codewars.com;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @see <a href="https://www.codewars.com/kata/5672682212c8ecf83e000050">Twice linear</a>
 */
public class TwiceLinear {

  public static void main(String[] args) {
    System.out.println(dblLinear(20));
  }

  public static int dblLinearBest(int n) {
    if (n == 0) return 1;
    SortedSet<Integer> u = new TreeSet<>();
    u.add(1);
    for (int i = 0; i < n; i++) {
      int x = u.first();
      u.add(x * 2 + 1);
      u.add(x * 3 + 1);
      u.remove(x);
    }
    return u.first();
  }

  public static int dblLinear(int n) {
    int limit = pow(n); //  + 2 пришлось увеличивать для тестов
    //  так как некоторые элементы не попадают в лимит
    System.out.println("limit = " + limit);
    System.out.println(
        "limit all elements 1+2+4+8+16... = 2^(n-1)-1 = " + ((int) Math.pow(2, limit + 1) - 1));
    Tree root = new Tree(1, 0);
    root.generateTree(root, limit);
    // распечатка
    printTree(root, null, false);
    List<Integer> result = new ArrayList<>();
    result.add(root.value);
    // заполнить итоговый лист
    root.all(root, result);
    var r = result.stream().distinct().sorted().toList();
    System.out.println("size = " + r.size());
    return r.get(n);
  }

  private static int pow(int n) {
    if (n < 2) return 0;
    return 1 + pow(n / 2);
  }

  static class Tree {
    int value;
    int level;
    Tree left;
    Tree right;

    public Tree(int value, int level) {
      this.value = value;
      this.level = level;
      this.left = null;
      this.right = null;
    }

    public void generateTree(Tree root, int limit) {
      if (root.level < limit) {
        Tree currentLeft = addLeft(root.value, root.level);
        root.left = currentLeft;
        generateTree(currentLeft, limit);
        Tree currentRight = addRight(root.value, root.level);
        root.right = currentRight;
        generateTree(currentRight, limit);
      }
    }

    public Tree addRight(int value, int level) {
      return new Tree(value * 3 + 1, level + 1);
    }

    public Tree addLeft(int value, int level) {
      return new Tree(value * 2 + 1, level + 1);
    }

    public void all(Tree root, List<Integer> result) {
      if (root.left != null) {
        result.add(root.left.value);
        all(root.left, result);
      }
      if (root.right != null) {
        result.add(root.right.value);
        all(root.right, result);
      }
    }
  }
  // распечатать дерево
  static class Trunk {
    Trunk prev;
    String str;

    Trunk(Trunk prev, String str) {
      this.prev = prev;
      this.str = str;
    }
  }

  public static void showTrunks(Trunk p) {
    if (p == null) {
      return;
    }

    showTrunks(p.prev);
    System.out.print(p.str);
  }

  public static void printTree(Tree root, Trunk prev, boolean isLeft) {
    if (root == null) {
      return;
    }

    String prev_str = "    ";
    Trunk trunk = new Trunk(prev, prev_str);

    printTree(root.right, trunk, true);

    if (prev == null) {
      trunk.str = "———";
    } else if (isLeft) {
      trunk.str = ".———";
      prev_str = "   |";
    } else {
      trunk.str = "`———";
      prev.str = prev_str;
    }

    showTrunks(trunk);
    System.out.println(" " + root.value);

    if (prev != null) {
      prev.str = prev_str;
    }
    trunk.str = "   |";

    printTree(root.left, trunk, false);
  }
}
