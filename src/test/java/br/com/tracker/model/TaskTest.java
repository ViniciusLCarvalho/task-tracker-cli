package br.com.tracker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {
    private Task task;

    private void waitTimestamp() throws InterruptedException {
        Thread.sleep(10);
    }

    @BeforeEach
    void setUp() {
        task = new Task(1, "buy eggs");
    }

    @Test
    public void shouldCreateTaskWithDefaultValues() {
        assertEquals(1, task.getId());
        assertEquals("buy eggs", task.getDescription());
        assertEquals(Status.TODO, task.getStatus());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
    }

    @Test
    public void shouldChangeTaskDescription() throws InterruptedException {
        LocalDateTime oldUpdatedAt = task.getUpdatedAt();
        LocalDateTime createdAt = task.getCreatedAt();

        waitTimestamp();

        task.setDescription("Buy brown eggs");

        assertEquals("Buy brown eggs", task.getDescription());
        assertEquals(task.getCreatedAt(), createdAt);
        assertTrue(task.getUpdatedAt().isAfter(oldUpdatedAt));

    }

    @Test
    public void shouldChangeTaskStatus() throws InterruptedException {
        LocalDateTime oldUpdatedAt = task.getUpdatedAt();
        LocalDateTime createdAt = task.getCreatedAt();

        waitTimestamp();

        task.setStatus(Status.IN_PROGRESS);

        assertEquals(Status.IN_PROGRESS, task.getStatus());
        assertEquals(task.getCreatedAt(), createdAt);
        assertTrue(task.getUpdatedAt().isAfter(oldUpdatedAt));
    }

}
