package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IFileAnalyzer {

    List<String> readFile(String filePath);

    List<Line> parseLines(List<String> fileContent);

    int getTotalCar(List<Line> lines);

    Map<LocalDate, Integer> getTotalCarPerDay(List<Line> lines);

    List<Line> getTopKLineWithMostNumberOfCar(List<Line> lines, int k);

    LocalDateTime[] getPeriodWithLeastCar(List<Line> lines, int periodSize);
}
