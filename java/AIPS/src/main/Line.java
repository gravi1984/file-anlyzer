package main;

import java.time.LocalDateTime;

public class Line {

    public String OriginalLine;
    LocalDateTime Time;
    int NumberOfCar;

    public int getNumberOfCar() {
        return NumberOfCar;
    }

    public Line(String originalLine, LocalDateTime time, int numberOfCar) {
        OriginalLine = originalLine;
        Time = time;
        NumberOfCar = numberOfCar;
    }
}
