package br.com.tracker.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import br.com.tracker.model.Status;
import br.com.tracker.model.Task;

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

    @Test
    void shouldSaveAndFindTask() throws IOException {
        Path tempFile = tempDir.resolve("tasks.json");
        TaskRepository repository = new TaskRepository(tempFile);

        Task task = new Task(1, "Buy eggs");

        repository.saveAll(List.of(task));

        List<Task> tasks = repository.findAll();

        assertEquals(1, tasks.size());

        Task savedTask = tasks.get(0);

        assertEquals(1, savedTask.getId());
        assertEquals("Buy eggs", savedTask.getDescription());
        assertEquals(Status.TODO, savedTask.getStatus());
        assertEquals(task.getCreatedAt(), savedTask.getCreatedAt());
        assertEquals(task.getUpdatedAt(), savedTask.getUpdatedAt());

    }

    @Test
    void shouldSaveAndFindMultipleTasks() throws IOException {
        Path tempFile = tempDir.resolve("tasks.json");
        TaskRepository repository = new TaskRepository(tempFile);

        Task task1 = new Task(1, "Buy eggs");
        Task task2 = new Task(2, "Study Java");
        Task task3 = new Task(3, "Walk the dog");

        repository.saveAll(List.of(task1, task2, task3));

        List<Task> tasks = repository.findAll();

        assertEquals(3, tasks.size());

        assertEquals(1, tasks.get(0).getId());
        assertEquals("Buy eggs", tasks.get(0).getDescription());
        assertEquals(Status.TODO, tasks.get(0).getStatus());
        assertEquals(task1.getCreatedAt(), tasks.get(0).getCreatedAt());
        assertEquals(task1.getUpdatedAt(), tasks.get(0).getUpdatedAt());

        assertEquals(2, tasks.get(1).getId());
        assertEquals("Study Java", tasks.get(1).getDescription());
        assertEquals(Status.TODO, tasks.get(1).getStatus());
        assertEquals(task2.getCreatedAt(), tasks.get(1).getCreatedAt());
        assertEquals(task2.getUpdatedAt(), tasks.get(1).getUpdatedAt());

        assertEquals(3, tasks.get(2).getId());
        assertEquals("Walk the dog", tasks.get(2).getDescription());
        assertEquals(Status.TODO, tasks.get(2).getStatus());
        assertEquals(task3.getCreatedAt(), tasks.get(2).getCreatedAt());
        assertEquals(task3.getUpdatedAt(), tasks.get(2).getUpdatedAt());
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoTasks() throws IOException {
        Path tempFile = tempDir.resolve("tasks.json");
        TaskRepository repository = new TaskRepository(tempFile);

        List<Task> tasks = repository.findAll();

        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldSaveAndFindTasksWithDifferentStatus() throws IOException {
        Path tempFile = tempDir.resolve("tasks.json");
        TaskRepository repository = new TaskRepository(tempFile);

        Task todo = new Task(1, "Buy eggs");
        Task inProgress = new Task(2, "Study Java");
        Task done = new Task(3, "Walk the dog");

        inProgress.setStatus(Status.IN_PROGRESS);
        done.setStatus(Status.DONE);

        repository.saveAll(List.of(todo, inProgress, done));
        List<Task> tasks = repository.findAll();

        assertEquals(Status.TODO, tasks.get(0).getStatus());
        assertEquals(Status.IN_PROGRESS, tasks.get(1).getStatus());
        assertEquals(Status.DONE, tasks.get(2).getStatus());
    }
}