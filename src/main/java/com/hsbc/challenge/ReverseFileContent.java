package com.hsbc.challenge;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ReverseFileContent {

    Logger log = Logger.getLogger(ReverseFileContent.class.getName());

    public static void main(String[] args) {
        Path inputFile = Paths.get("src/main/resources/inputFile.txt");
        Path outputFile = Paths.get("src/main/resources/outputFile.txt");
        try {
            reverseContent(inputFile, outputFile);
            System.out.println("File content reversed successfully.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public static void reverseContent(Path inputPath, Path outputPath) throws IOException {

        List<String> lines = Files.lines(inputPath)
                                  .collect(Collectors.toList());

        // Reverse each line and write to the output file
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath)) {
            for (int i = lines.size() - 1; i >= 0; i--) {
                String reversedLine = new StringBuilder(lines.get(i)).reverse().toString();
                writer.write(reversedLine);
                writer.newLine();
            }
        }
    }
}
