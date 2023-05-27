package codewars.com;

import java.util.*;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 *
 */
public class CommandPattern {
    public static void main(String[] args) {
        System.out.println((Arrays.toString(parse("iiirjrjsdoso"))));
    }


    /**
     * @param data i - increment d - decrement s - square o - output <br>
     *     any another symbol ignore
     * @return int[] - result
     */
    public static int[] parse(String data) {
        List<Integer> result = new ArrayList<>();
        int counter = 0;
        CommandFactory commandFactory = new CommandFactory(result);
        for (String code : data.split("")) {
            Command command = commandFactory.getCommand(code);
            counter = command.execute(counter);
        }
        return result.stream().mapToInt(i -> i).toArray();
    }

    public static int[] parseOperation(String data) {
        Map<String, UnaryOperator<Integer>> commandMap = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        commandMap.put("i", number -> ++number);
        commandMap.put("d", number -> --number);
        commandMap.put("s", number -> number * number);
        commandMap.put(
                "o",
                number -> {
                    list.add(number);
                    return number;
                });
        int result = 0;
        for (String command : data.split("")) {
            result = commandMap.getOrDefault(command, number -> number).apply(result);
        }
        return list.stream().mapToInt(x -> x).toArray();
    }

    /**
     * @see <a href="https://www.codewars.com/kata/583f158ea20cfcbeb400000a">
     *     Make a function that does arithmetic!
     *     </a>
     * @param a b numbers
     * @param operator text description of operation
     * @return result
     */
    public static int arithmetic(int a, int b, String operator) {
        Map<String, BinaryOperator<Integer>> commandMap = new HashMap<>();

        commandMap.put("add", Integer::sum);
        commandMap.put("subtract", (x, y) -> x - y);
        commandMap.put("multiply", (x, y) -> x * y);
        commandMap.put("divide", (x, y) -> x / y);

        return commandMap.get(operator).apply(a, b);
    }
}

class CommandFactory {
    private final List<Integer> result;

    public CommandFactory(List<Integer> result) {
        this.result = result;
    }

    Command getCommand(String code) {
        return switch (code) {
            case "i" -> new Increment();
            case "d" -> new Decrement();
            case "s" -> new Square();
            case "o" -> new Output(result);
            default -> new Ignore();
        };
    }
}

class Increment implements Command {

    @Override
    public int execute(int number) {
        return ++number;
    }
}

class Decrement implements Command {

    @Override
    public int execute(int number) {
        return --number;
    }
}

class Square implements Command {

    @Override
    public int execute(int number) {
        return number * number;
    }
}

class Ignore implements Command {

    @Override
    public int execute(int number) {
        return number;
    }
}

class Output implements Command {
    private final List<Integer> result;

    public Output(List<Integer> result) {
        this.result = result;
    }

    @Override
    public int execute(int number) {
        result.add(number);
        return number;
    }

}
@FunctionalInterface
interface Command {
    int execute(int number);
}
