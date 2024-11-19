package com.hsbc.challenge;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReverseFileContentTest {

    private static final Path TEMP_INPUT_FILE = Paths.get("tempInput.txt");
    private static final Path TEMP_OUTPUT_FILE = Paths.get("tempOutput.txt");

    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TEMP_INPUT_FILE);
        Files.deleteIfExists(TEMP_OUTPUT_FILE);
    }

    @Test
    void testReverseContent_normalFile() throws IOException {
        List<String> sampleContent = Arrays.asList(
                "Hello World",
                "This is a test",
                "Reverse these lines"
        );
        Files.write(TEMP_INPUT_FILE, sampleContent);

        List<String> expectedContent = Arrays.asList(
                "senil eseht esreveR"
                ,"tset a si sihT"
                ,"dlroW olleH"
        );

        ReverseFileContent.reverseContent(TEMP_INPUT_FILE, TEMP_OUTPUT_FILE);

        // Assert: Read the output file and check if the content is reversed
        List<String> actualContent = Files.readAllLines(TEMP_OUTPUT_FILE, StandardCharsets.US_ASCII);
        assertEquals(expectedContent, actualContent);
    }

    @Test
    void testReverseContent_emptyFile() throws IOException {
        Files.write(TEMP_INPUT_FILE, Collections.emptyList());

        ReverseFileContent.reverseContent(TEMP_INPUT_FILE, TEMP_OUTPUT_FILE);

        // Assert: The output file should also be empty
        List<String> actualContent = Files.readAllLines(TEMP_OUTPUT_FILE, StandardCharsets.US_ASCII);
        assertTrue(actualContent.isEmpty());
    }

    @Test
    void testReverseContent_singleLineFile() throws IOException {
        Files.write(TEMP_INPUT_FILE, Collections.singletonList("Single line"));

        ReverseFileContent.reverseContent(TEMP_INPUT_FILE, TEMP_OUTPUT_FILE);

        // Assert: The output file should contain the reversed single line
        List<String> actualContent = Files.readAllLines(TEMP_OUTPUT_FILE, StandardCharsets.US_ASCII);
        assertEquals(Collections.singletonList("enil elgniS"), actualContent);
    }

    @Test
    void testReverseContent_largeFile() throws IOException {
        // Arrange: Create a large file with many lines
        List<String> largeContent = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeContent.add("Line " + i);
        }
        Files.write(TEMP_INPUT_FILE, largeContent);

        ReverseFileContent.reverseContent(TEMP_INPUT_FILE, TEMP_OUTPUT_FILE);

        // Assert: Verify the output is correctly reversed
        List<String> actualContent = Files.readAllLines(TEMP_OUTPUT_FILE, StandardCharsets.US_ASCII);

        List<String> expectedContent = largeContent.stream().map((s)-> new StringBuilder(s).reverse().toString()).collect(Collectors.toList());
        Collections.reverse(expectedContent);

        assertEquals(expectedContent, actualContent);
    }

    @Test
    void testReverseContent_preservesLineBreaks() throws IOException {
        // Arrange: Create a file with lines and line breaks
        List<String> sampleContent = Arrays.asList(
                "Line 1",
                "Line 2",
                "Line 3"
        );
        Files.write(TEMP_INPUT_FILE, sampleContent);

        ReverseFileContent.reverseContent(TEMP_INPUT_FILE, TEMP_OUTPUT_FILE);

        // Assert: Check that the content is reversed and line breaks are preserved
        List<String> expectedContent = Arrays.asList(
                "3 eniL",
                "2 eniL",
                "1 eniL"
        );
        List<String> actualContent = Files.readAllLines(TEMP_OUTPUT_FILE);
        assertEquals(expectedContent, actualContent);
    }
}
