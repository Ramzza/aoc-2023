
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    private static final String INPUT_BASE_PATH = "/home/ramza/AA/ws/aoc-2023/day-01/resources/";
    private static final String DEFAULT_INPUT = "d1.in";

    public static void main(String[] args) throws Exception {
        ArrayList<String> inputByLines = readInputFromFile(args);

        int result = inputByLines.stream()
                .map(App::getNumberFromLine)
                .reduce(0, Integer::sum);

        System.out.println("Result: " + result);
    }

    private static int getNumberFromLine(String inputLine) {
        List<String> charList = Arrays.asList(inputLine.split(""));

        return Integer.parseInt(""
                + getFirstNumberFromString(charList)
                + getFirstNumberFromString(charList.reversed()));
    }

    private static long getFirstNumberFromString(List<String> charList) {
        int number = 0;
        int i = 0;
        boolean isFound = false;

        while (!isFound && i < charList.size()) {
            String currentCharacter = charList.get(i);

            try {
                number = Integer.parseInt(currentCharacter);
                isFound = true;
            } catch (NumberFormatException e) {
                // do nothing
            }

            i++;
        }

        if (!isFound) {
            System.out.println("No number found in: " + charList);
        }

        return number;
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
