
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
    private static final String DEFAULT_INPUT = "d5_ex.in";

    public static void main(String[] args) throws Exception {
        ArrayList<String> inputByLines = readInputFromFile(args);

        List<Long> inputSeeds = getSeedsFromInput(inputByLines);
        List<Map<Long, MapEntry>> mapsBySources = getMapsFromInput(inputByLines);

        long lowestLocation = inputSeeds.stream()
                .map(seed -> getLocationForSeed(seed, mapsBySources))
                .reduce(Long.MAX_VALUE, Long::min);

        System.out.println("Lowest location number: " + lowestLocation);
    }

    private static long getLocationForSeed(long inputSeed, List<Map<Long, MapEntry>> mapsBySources) {
        long mappedValue = inputSeed;

        for (int i = 0; i < mapsBySources.size(); i++) {
            mappedValue = getDestinationFromMap(mappedValue, mapsBySources.get(i));
        }

        return mappedValue;
    }

    private static long getDestinationFromMap(long source, Map<Long, MapEntry> mapBySources) {
        long highestLowerSource = mapBySources.keySet().stream().filter(key -> key <= source).reduce(-1L, Long::max);

        // source is lower than the lowest existing mapping
        if (highestLowerSource == -1) {
            return source;
        }

        MapEntry mapping = mapBySources.get(highestLowerSource);

        return source > mapping.source + mapping.range ? source : mapping.destination + (source - mapping.source);
    }

    private static List<Map<Long, MapEntry>> getMapsFromInput(ArrayList<String> inputLines) {
        List<Map<Long, MapEntry>> mapsBySources = new ArrayList<>();

        inputLines.forEach(inputLine -> {
            if (inputLine.contains("map")) {
                mapsBySources.add(new HashMap<>());
            }

            if (Pattern.matches("[\\d ]+", inputLine)) {
                List<Long> mapEntryValues = List.of(inputLine.split(" ")).stream().map(Long::parseLong).toList();
                mapsBySources.getLast().put(mapEntryValues.get(1), new MapEntry(mapEntryValues));
            }
        });

        return mapsBySources;
    }

    private static List<Long> getSeedsFromInput(ArrayList<String> inputByLines) {
        return List.of(
                inputByLines.get(0)
                        .replace("seeds: ", "")
                        .split(" ")).stream()
                .map(Long::parseLong)
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

        private final long destination;
        private final long source;
        private final long range;

        public MapEntry(List<Long> mapEntryValues) {
            this.destination = mapEntryValues.get(0);
            this.source = mapEntryValues.get(1);
            this.range = mapEntryValues.get(2);
        }
    }
}
