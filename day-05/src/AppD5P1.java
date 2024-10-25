
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class AppD5P1 {

    private static final String INPUT_BASE_PATH = "/home/ramza/AA/ws/aoc-2023/day-05/resources/";
    private static final String DEFAULT_INPUT = "d5.in";

    public static void main(String[] args) throws Exception {
        ArrayList<String> inputByLines = readInputFromFile(args);

        List<Integer> inputSeeds = getSeedsFromInput(inputByLines);
        List<Map<Integer, MapEntry>> almanacMaps = getMapsFromInput(inputByLines);

        int lowestLocation = inputSeeds.stream()
                .map(seed -> getLocationForSeed(seed, almanacMaps))
                .reduce(Integer.MAX_VALUE, Integer::min);

        System.out.println("Lowest location number: " + lowestLocation);
    }

    private static int getLocationForSeed(int inputSeed, List<Map<Integer, MapEntry>> almanacMaps) {
        return 0;
    }

    private static List<Map<Integer, MapEntry>> getMapsFromInput(ArrayList<String> inputLines) {
        List<Map<Integer, MapEntry>> almanacMaps = new ArrayList<>();

        inputLines.forEach(inputLine -> {
            if (inputLine.contains("map")) {
                almanacMaps.add(new HashMap<>());
            }

            if (Pattern.matches("[\\d ]+", inputLine)) {
                List<Integer> mapEntryValues = List.of(inputLine.split(" ")).stream().map(Integer::parseInt).toList();
                almanacMaps.getLast().put(mapEntryValues.get(0), new MapEntry(mapEntryValues));
            }
        });

        return almanacMaps;
    }

    private static List<Integer> getSeedsFromInput(ArrayList<String> inputByLines) {
        return List.of(
                inputByLines.get(0)
                        .replace("seeds: ", "")
                        .split(" ")).stream()
                .map(Integer::parseInt)
                .toList();
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

    private static class MapEntry {

        private final int destination;
        private final int source;
        private final int range;

        public MapEntry(List<Integer> mapEntryValues) {
            this.destination = mapEntryValues.get(0);
            this.source = mapEntryValues.get(1);
            this.range = mapEntryValues.get(2);
        }

        public int getDestination() {
            return destination;
        }

        public int getSource() {
            return source;
        }

        public int getRange() {
            return range;
        }
    }
}
