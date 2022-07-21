
package main;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileAnalyzer implements IFileAnalyzer {

    public List<String> readFile(String filePath) {
        List<String> fileContent = null;
        /* Auto-close IO stream with the syntax */
        try (Stream<String> fileLines = Files.lines(Paths.get(filePath))) {
            fileContent = fileLines.collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Error when reading file.");
            e.printStackTrace();
        } finally {
            return fileContent;
        }
    }

    public List<Line> parseLines(List<String> fileContent) {
        List<Line> lines = new ArrayList<Line>();
        for (String line : fileContent) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT);
            LocalDateTime time = LocalDateTime.parse(line.split("\\s+")[0], formatter);
            int numberOfCab = Integer.parseInt(line.split("\\s+")[1]);
            lines.add(new Line(line, time, numberOfCab));
        }
        return lines;
    }

    public int getTotalCar(List<Line> lines) {
        return deduplicateLinesByTime(lines).stream().mapToInt(l -> l.NumberOfCar).sum();
    }

    public Map<LocalDate, Integer> getTotalCarPerDay(List<Line> lines) {
        return deduplicateLinesByTime(lines)
                .stream()
                .collect(Collectors.groupingBy(
                        l -> l.Time.toLocalDate(),
                        Collectors.summingInt(l -> l.NumberOfCar)
                ));
    }

    public List<Line> getTopKLineWithMostNumberOfCar(List<Line> lines, int k) {
        List<Line> linesWithoutDuplicateTime = deduplicateLinesByTime(lines);

        List<Line> linesSortedByNumberOfCar = linesWithoutDuplicateTime.stream()
                .sorted(Comparator.comparing(Line::getNumberOfCar).reversed())
                .collect(Collectors.toList());
        return linesSortedByNumberOfCar.stream().limit(k).collect(Collectors.toList());
    }

    public LocalDateTime[] getPeriodWithLeastCar(List<Line> lines, int periodSize) {

        List<Line> linesWithoutDuplicateTime = deduplicateLinesByTime(lines);

        Map<LocalDateTime, Line> records = linesWithoutDuplicateTime.stream().collect(Collectors.toMap(l -> l.Time, Line -> Line));

        if (records.size() < periodSize) {
            System.out.println("Not found " + periodSize * 0.5 + " hour data.");
            return null;
        } else {
            LocalDateTime[] periods = null;
            int leastCar = Integer.MAX_VALUE;

            for (LocalDateTime t0 : records.keySet()) {
                LocalDateTime[] currentPeriods = new LocalDateTime[periodSize];
                currentPeriods[0] = t0;
                for (int i = 1; i < periodSize; i++) {
                    currentPeriods[i] = currentPeriods[i - 1].plusMinutes(30);
                }

                if (records.keySet().containsAll(Arrays.asList(currentPeriods))) {
                    int periodCarSum = 0;
                    for (LocalDateTime t : currentPeriods) {
                        periodCarSum += records.get(t).NumberOfCar;
                    }
                    if (periodCarSum < leastCar) {
                        leastCar = periodCarSum;
                        periods = currentPeriods.clone();
                    }
                }
            }
            if (periods == null) {
                System.out.println("Not found " + periodSize * 0.5 + " hour data.");
                return null;
            } else {
                for (LocalDateTime period : periods) {
                    System.out.println(records.get(period).OriginalLine);
                }
                return periods;
            }
        }

    }

    public List<Line> deduplicateLinesByTime(List<Line> lines) {
        return lines.
                stream().
                collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(l -> l.Time)))).
                stream().
                collect(Collectors.toList());
    }


}


