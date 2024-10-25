
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AppD5P1 {

    private static final String INPUT_BASE_PATH = "/home/ramza/AA/ws/aoc-2023/day-05/resources/";
    private static final String DEFAULT_INPUT = "d5.in";

    public static void main(String[] args) throws Exception {
        ArrayList<String> inputByLines = readInputFromFile(args);
        boolean isTextSearchActive = args.length > 1 ? Boolean.parseBoolean(args[1]) : true;

        System.out.println("Result: " + isTextSearchActive);
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
