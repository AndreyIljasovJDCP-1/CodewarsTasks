package codewars.com;

import java.util.Arrays;
import java.util.stream.IntStream;


public class Minesweeper {
    public static final char BOMB = 'x';
    public static final char EMPTY = '-';
    public static final char ZERO = '0';

    public static void main(String[] args) {
        int[][] bombs = new int[][] {{0, 0}, {0, 2}, {1, 1}, {1, 2}, {3, 1}, {3, 2}, {3, 3}};
        getField(bombs, 5);
    }

    /**
     * Print field with BOMBS and count of bombs around
     * @param bombs coordinate of BOMBS
     * @param fieldSize size of field
     */
    public static void getField(int[][] bombs, int fieldSize) {

        char[][] field = new char[fieldSize + 2][fieldSize + 2];
        Arrays.stream(field).forEach(row -> Arrays.fill(row, ZERO));
        for (int[] bomb : bombs) {
            int x = bomb[0];
            int y = bomb[1];
            field[x + 1][y + 1] = BOMB;
            fillAround(field, x + 1, y + 1);
        }
        printField(field);
    }

    private static void fillAround(char[][] field, int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (field[i][j] != BOMB) {
                    field[i][j]++;
                }
            }
        }
    }

    private static void printField(char[][] field) {
        IntStream.range(0, field.length - 1).mapToObj(i -> i + " ").forEach(System.out::print);
        System.out.println();
        for (int j = 1; j < field.length - 1; j++) {
            System.out.print(j + "|");
            for (int k = 1; k < field.length - 1; k++) {
                if (field[j][k] == ZERO) {
                    field[j][k] = EMPTY;
                }
                System.out.print(field[j][k] + "|");
            }
            System.out.println();
        }
        System.out.println("-".repeat((field.length - 1) * 2));
    }
}
