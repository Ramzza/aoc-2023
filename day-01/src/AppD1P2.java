
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppD1P2 {

    private static final String INPUT_BASE_PATH = "/home/ramza/AA/ws/aoc-2023/day-01/resources/";
    private static final String DEFAULT_INPUT = "d1.in";
    private static final Map<String, Integer> NUMBERS_BY_NUMBERSTRINGS = Map.of("one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8, "nine", 9);

    public static void main(String[] args) throws Exception {
        ArrayList<String> inputByLines = readInputFromFile(args);
        boolean isTextSearchActive = args.length > 1 ? Boolean.parseBoolean(args[1]) : true;

        int result = inputByLines.stream()
                .map(inputLine -> getNumberFromLine(inputLine, isTextSearchActive))
                .reduce(0, Integer::sum);

        System.out.println("Result: " + result);
    }

    private static int getNumberFromLine(String inputLine, boolean isTextSearchActive) {
        List<String> valuesToFind = new ArrayList<>();
        valuesToFind.addAll(getNumberListToFind());

        if (isTextSearchActive) {
            valuesToFind.addAll(NUMBERS_BY_NUMBERSTRINGS.keySet());
        }

        int firstNumber = getFirstNumberFromLine(inputLine, valuesToFind);
        int lastNumber = getLastNumberFromLine(inputLine, valuesToFind);
        int lineNumber = firstNumber * 10 + lastNumber;

        return lineNumber;
    }

    private static int getFirstNumberFromLine(String inputLine, List<String> valuesToFind) {
        int resultNumber;

        String valueOfFirstMatchingLocation = getValueOfFirstMatchingLocation(inputLine, valuesToFind);

        try {
            resultNumber = Integer.parseInt(valueOfFirstMatchingLocation);
        } catch (NumberFormatException e) {
            resultNumber = NUMBERS_BY_NUMBERSTRINGS.get(valueOfFirstMatchingLocation);
        }

        return resultNumber;
    }

    private static int getLastNumberFromLine(String inputLine, List<String> valuesToFind) {
        int resultNumber;

        String reversedInput = reverseString(inputLine);
        List<String> reversedValuesToFind = valuesToFind.stream()
                .map(AppD1P2::reverseString).toList();

        String valueOfFirstMatchingLocation = getValueOfFirstMatchingLocation(reversedInput, reversedValuesToFind);

        try {
            resultNumber = Integer.parseInt(valueOfFirstMatchingLocation);
        } catch (NumberFormatException e) {
            resultNumber = NUMBERS_BY_NUMBERSTRINGS.get(reverseString(valueOfFirstMatchingLocation));
        }

        return resultNumber;
    }

    private static String getValueOfFirstMatchingLocation(String inputLine, List<String> valuesToFind) {
        int firstMatchingLocation = inputLine.length();
        String valueOfFirstMatchingLocation = "";

        for (int i = 0; i < valuesToFind.size(); i++) {
            String valueToFind = valuesToFind.get(i);
            int currentLocation = inputLine.indexOf(valueToFind);

            if (currentLocation > -1 && currentLocation < firstMatchingLocation) {
                valueOfFirstMatchingLocation = valueToFind;
                firstMatchingLocation = currentLocation;
            }
        }

        return valueOfFirstMatchingLocation;
    }

    private static String reverseString(String inputStr) {
        return new StringBuilder(inputStr).reverse().toString();
    }

    private static List<String> getNumberListToFind() {
        return NUMBERS_BY_NUMBERSTRINGS
                .values().stream()
                .map(nr -> nr.toString()).toList();
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
