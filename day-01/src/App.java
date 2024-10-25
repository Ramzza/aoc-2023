
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {

    private static final String INPUT_BASE_PATH = "/home/ramza/AA/ws/aoc-2023/day-01/resources/";
    private static final String DEFAULT_INPUT = "d1.in";
    private static final Map<String, Integer> NUMBERS_BY_NUMBER_STRINGS = Map.of("one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8, "nine", 9);

    public static void main(String[] args) throws Exception {
        ArrayList<String> inputByLines = readInputFromFile(args);

        int result = inputByLines.stream()
                .map(App::getNumberFromLineWithText)
                .reduce(0, Integer::sum);

        System.out.println("Result: " + result);
    }

    private static int getNumberFromLineWithText(String inputLine) {
        String reversedInput = reverseString(inputLine);
        List<String> valuesToFind = new ArrayList<>();
        valuesToFind.addAll(NUMBERS_BY_NUMBER_STRINGS
                .values().stream()
                .map(nr -> nr.toString()).toList());
        valuesToFind.addAll(NUMBERS_BY_NUMBER_STRINGS.keySet());
        List<String> reversedValuesToFind = valuesToFind.stream()
                .map(App::reverseString).toList();

        return getNumberFromLine(inputLine, valuesToFind, reversedInput, reversedValuesToFind);
    }

    private static int getNumberFromLineWithoutText(String inputLine) {
        String reversedInput = reverseString(inputLine);
        List<String> valuesToFind = NUMBERS_BY_NUMBER_STRINGS
                .values().stream()
                .map(nr -> nr.toString()).toList();

        return getNumberFromLine(inputLine, valuesToFind, reversedInput, valuesToFind);
    }

    private static int getNumberFromLine(String inputLine, List<String> valuesToFind, String reversedInput, List<String> valuesToFindReversed) {
        int firstNumber = getFirstNumberFromLine(inputLine, valuesToFind, false);
        int lastNumber = getFirstNumberFromLine(reversedInput, valuesToFindReversed, true);
        int lineNumber = firstNumber * 10 + lastNumber;

        return lineNumber;
    }

    private static int getFirstNumberFromLine(String inputLine, List<String> valuesToFind, boolean isReversed) {
        int minLocation = inputLine.length();
        String valueOfMinLocation = "";
        int resultNumber;

        for (int i = 0; i < valuesToFind.size(); i++) {
            int currentLocation = inputLine.indexOf(valuesToFind.get(i));

            if (currentLocation > -1 && currentLocation < minLocation) {
                valueOfMinLocation = valuesToFind.get(i);
                minLocation = currentLocation;
            }
        }

        try {
            resultNumber = Integer.parseInt(valueOfMinLocation);
        } catch (NumberFormatException e) {
            resultNumber = isReversed
                    ? NUMBERS_BY_NUMBER_STRINGS.get(reverseString(valueOfMinLocation))
                    : NUMBERS_BY_NUMBER_STRINGS.get(valueOfMinLocation);
        }

        return resultNumber;
    }

    private static String reverseString(String inputStr) {
        return new StringBuilder(inputStr).reverse().toString();
    }

    private static ArrayList<String> readInputFromFile(String[] args) {
        String inputFileName = DEFAULT_INPUT;
        ArrayList<String> resultFileByLines = new ArrayList<>();

        if (args.length > 0) {
            inputFileName = args[0];
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_BASE_PATH + inputFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                resultFileByLines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error when reading input: " + e.getMessage());
        }

        return resultFileByLines;
    }
}
