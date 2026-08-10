package br.com.tracker.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class RepositoryTest {
    @TempDir
    Path tempDir;

    @Test
    void shouldCreateFileIfNotExists() throws IOException {
        Path tempFile = tempDir.resolve("tasks.json");

        assertFalse(Files.exists(tempFile));

        new TaskRepository(tempFile);

        assertTrue(Files.exists(tempFile));
    }

    @Test
    void shouldCreateEmptyJsonFile() throws IOException {
        Path tempFile = tempDir.resolve("tasks.json");

        new TaskRepository(tempFile);

        String content = Files.readString(tempFile);

        assertEquals("[]", content);
    }
}