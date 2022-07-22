package test;

import main.FileAnalyzer;
import main.IFileAnalyzer;
import main.Line;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FileAnalyzerTest {
    private IFileAnalyzer fileAnalyzer;

    @BeforeEach
    public void setup() {
        fileAnalyzer = new FileAnalyzer();
    }
    

    // We can have a FileService that handle reading files, so below can be tested thoroughly
    // Now I'm using Files.lines to read from path, which is a static method that hard to mock
    @Test
    @Disabled
    void ReadFile_ValidFile_Returns() {
    }

    @Test
    @Disabled
    void ReadFile_EmptyFile_Returns() {
    }

    @Test
    @Disabled
    void ReadFile_NonExistingFile_Throws() {
    }

    @Test
    void ParseLine_ValidFileContent_ReturnsListOfLine() {
        // Arrange
        List<String> fileLines = Arrays.asList("2021-12-01T05:00:00 5", "2021-12-05T11:30:00 7");

        // Act
        List<Line> result = fileAnalyzer.parseLines(fileLines);

        // Assert
        assertEquals(2, result.size());

    }

    @Test
    void GetTotalCar_ValidLines_ReturnsTotalCar() {
        // Arrange
        List<Line> lines = Arrays.asList(
                new Line("2021-12-01T05:00:00 5", LocalDateTime.parse("2021-12-01T05:00:00"), 5),
                new Line("2021-12-05T15:30:00 15", LocalDateTime.parse("2021-12-05T15:30:00"), 15)
        );

        // Act
        int result = fileAnalyzer.getTotalCar(lines);

        // Assert
        assertEquals(20, result);

    }

    @Test
    void GetTotalCarPerDay_ValidLines_ReturnsTotalCarPerDay() {
        // Arrange
        List<Line> lines = Arrays.asList(
                new Line("2021-12-01T05:00:00 5", LocalDateTime.parse("2021-12-01T05:00:00"), 5),
                new Line("2021-12-01T06:00:00 3", LocalDateTime.parse("2021-12-01T06:00:00"), 3),
                new Line("2021-12-05T15:30:00 15", LocalDateTime.parse("2021-12-05T15:30:00"), 15)
        );

        // Act
        Map<LocalDate, Integer> result = fileAnalyzer.getTotalCarPerDay(lines);

        // Assert
        assertEquals(2, result.size());
        assertEquals(8, result.get(LocalDate.of(2021, 12, 1)));
        assertEquals(15, result.get(LocalDate.of(2021, 12, 5)));

    }

    @Test
    void GetTopKLineWithMostNumberOfCar_ValidLinesAndK_ReturnsTopKLine() {
        // Arrange
        List<Line> lines = Arrays.asList(
                new Line("2021-12-01T05:00:00 5", LocalDateTime.parse("2021-12-01T05:00:00"), 5),
                new Line("2021-12-01T06:00:00 3", LocalDateTime.parse("2021-12-01T06:00:00"), 3),
                new Line("2021-12-05T15:30:00 15", LocalDateTime.parse("2021-12-05T15:30:00"), 15)
        );

        // Act
        List<Line> result = fileAnalyzer.getTopKLineWithMostNumberOfCar(lines, 2);

        // Assert
        assertEquals(2, result.size());
        assertEquals("2021-12-05T15:30:00 15", result.get(0).OriginalLine);
        assertEquals("2021-12-01T05:00:00 5", result.get(1).OriginalLine);

    }

    @Test
    void GetTopKLineWithMostNumberOfCar_FileHasLessThanKLines_ReturnsLines() {
        // Arrange
        List<Line> lines = Arrays.asList(
                new Line("2021-12-01T05:00:00 5", LocalDateTime.parse("2021-12-01T05:00:00"), 5),
                new Line("2021-12-05T15:30:00 15", LocalDateTime.parse("2021-12-05T15:30:00"), 15)
        );

        // Act
        List<Line> result = fileAnalyzer.getTopKLineWithMostNumberOfCar(lines, 4);

        // Assert
        assertEquals(2, result.size());
        assertEquals("2021-12-05T15:30:00 15", result.get(0).OriginalLine);
        assertEquals("2021-12-01T05:00:00 5", result.get(1).OriginalLine);

    }

    @Test
    void GetPeriodWithLeastCar_ValidLinesAndPeriodSize_ReturnsPeriod() {
        // Arrange
        List<Line> lines = Arrays.asList(
                new Line("2021-12-01T05:00:00 5", LocalDateTime.parse("2021-12-01T05:00:00"), 5),
                new Line("2021-12-01T05:30:00 12", LocalDateTime.parse("2021-12-01T05:30:00"), 12),
                new Line("22021-12-01T06:00:00 14", LocalDateTime.parse("2021-12-01T06:00:00"), 14),
                new Line("2021-12-05T15:00:00 15", LocalDateTime.parse("2021-12-05T15:00:00"), 15),
                new Line("2021-12-05T15:30:00 15", LocalDateTime.parse("2021-12-05T15:30:00"), 15),
                new Line("2021-12-05T16:00:00 15", LocalDateTime.parse("2021-12-05T16:00:00"), 15)
        );

        // Act
        LocalDateTime[] result = fileAnalyzer.getPeriodWithLeastCar(lines, 2);

        // Assert
        assertEquals(LocalDateTime.parse("2021-12-01T05:00:00"), result[0]);
        assertEquals(LocalDateTime.parse("2021-12-01T05:30:00"), result[1]);

    }

    @Test
    void GetPeriodWithLeastCar_NoEnoughDataForPeriodSize_ReturnsNull() {
        // Arrange
        List<Line> lines = Arrays.asList(
                new Line("2021-12-01T05:00:00 5", LocalDateTime.parse("2021-12-01T05:00:00"), 5),
                new Line("2021-12-01T05:30:00 12", LocalDateTime.parse("2021-12-01T05:30:00"), 12)
        );

        // Act
        LocalDateTime[] result = fileAnalyzer.getPeriodWithLeastCar(lines, 3);

        // Assert
        assertEquals(null, result);

    }

    @Test
    void GetPeriodWithLeastCar_NoContinuousDataForPeriodSize_ReturnsNull() {
        // Arrange
        List<Line> lines = Arrays.asList(
                new Line("2021-12-01T05:00:00 5", LocalDateTime.parse("2021-12-01T05:00:00"), 5),
                new Line("2021-12-01T05:30:00 12", LocalDateTime.parse("2021-12-01T05:30:00"), 12),
                new Line("2021-12-01T07:30:00 12", LocalDateTime.parse("2021-12-01T07:30:00"), 12)
        );

        // Act
        LocalDateTime[] result = fileAnalyzer.getPeriodWithLeastCar(lines, 3);

        // Assert
        assertEquals(null, result);

    }

    @Test
    void DeduplicateLinesByTime_LinesContainDuplicateDate_ReturnsDeduplicated() {
        // Arrange
        List<Line> lines = Arrays.asList(
                new Line("2021-12-01T05:00:00 5", LocalDateTime.parse("2021-12-01T05:00:00"), 5),
                new Line("2021-12-01T05:00:00 5", LocalDateTime.parse("2021-12-01T05:00:00"), 5),
                new Line("2021-12-01T05:30:00 12", LocalDateTime.parse("2021-12-01T05:30:00"), 12),
                new Line("2021-12-05T16:00:00 15", LocalDateTime.parse("2021-12-05T16:00:00"), 15)
        );

        // Act
        List<Line> result = fileAnalyzer.deduplicateLinesByTime(lines);

        // Assert
        assertEquals(3, result.size());

    }
}
