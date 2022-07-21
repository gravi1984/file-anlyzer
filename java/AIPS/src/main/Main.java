package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        IFileAnalyzer fileAnalyzer = new FileAnalyzer();

        // Read file from path
        String filePath = "./src/main/input.txt";
        List<String> fileContent = fileAnalyzer.readFile(filePath);

        if (fileContent == null || fileContent.isEmpty()) {
            System.out.println("No records to process.");
            return;
        }

        List<Line> lines = fileAnalyzer.parseLines(fileContent);

        // Get total number of car seen in file
        System.out.println("1. Total number of car seen: ");
        int totalCar = fileAnalyzer.getTotalCar(lines);
        System.out.println(totalCar);

        // Get total number of car per day
        System.out.println("2. Total number of car per day: ");
        Map<LocalDate, Integer> totalCarPerDay = fileAnalyzer.getTotalCarPerDay(lines);
        totalCarPerDay.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + " " + entry.getValue());
        });

        // Get top 3 half hour with most car
        System.out.println("3. Top K half hour with most cars: ");
        List<Line> linesSortedByNumberOfCar = fileAnalyzer.getTopKLineWithMostNumberOfCar(lines, 3);
        linesSortedByNumberOfCar.stream().forEach(x -> {
            System.out.println(x.OriginalLine);
        });

        // Get 1.5 hour period with least car
        int periodSize = 3;
        System.out.println("4. The " + periodSize * 0.5 + " hour period with least cars: ");
        LocalDateTime[] period = fileAnalyzer.getPeriodWithLeastCar(lines, periodSize);

    }
}



