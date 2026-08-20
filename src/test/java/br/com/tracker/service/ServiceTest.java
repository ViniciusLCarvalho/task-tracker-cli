package br.com.tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.tracker.model.Status;
import br.com.tracker.model.Task;

public class ServiceTest {
    private FakeTaskRepository repository;
    private TaskService service;

    @BeforeEach
    void setUp() throws Exception {
        repository = new FakeTaskRepository();
        service = new TaskService(repository);
    }

    @Test
    void shouldAddTask() throws Exception {
        int id = service.addTask("buy eggs");

        assertEquals(1, id);
        assertEquals(1, repository.getTasks().get(0).getId());
        assertEquals("buy eggs", repository.getTasks().get(0).getDescription());
    }

    @Test
    void shouldGenerateNexttaskId() throws Exception {
        service.addTask("buy eggs");
        service.addTask("buy milk");

        assertEquals(1, repository.getTasks().get(0).getId());
        assertEquals(2, repository.getTasks().get(1).getId());
    }

    @Test
    void shouldGenerateNextIdBasedOnHighestId() throws Exception {
        repository.getTasks().add(new Task(1, "task 1"));
        repository.getTasks().add(new Task(5, "task 5"));
        repository.getTasks().add(new Task(3, "task 3"));

        service.addTask("task 6");

        assertEquals(6, repository.getTasks().get(3).getId());
    }

    @Test
    void shouldUpdateTaskDescription() throws Exception {
        service.addTask("buy eggs");

        service.updateTask(1, "buy vegetables");

        assertEquals("buy vegetables", repository.getTasks().get(0).getDescription());
    }

    @Test
    void shouldUpdateOnlyTaskWithMatchingId() throws Exception {
        service.addTask("buy eggs");
        service.addTask("buy milk");

        service.updateTask(1, "buy vegetables");

        assertEquals("buy vegetables", repository.getTasks().get(0).getDescription());
        assertEquals("buy milk", repository.getTasks().get(1).getDescription());
    }

    @Test
    void shouldDeleteTask() throws Exception {
        service.addTask("buy eggs");
        service.addTask("buy milk");

        service.deleteTask(1);

        assertEquals(1, repository.getTasks().size());
        assertEquals(2, repository.getTasks().get(0).getId());
    }

    @Test
    void shouldListAllTasks() throws Exception {
        service.addTask("buy eggs");
        service.addTask("buy milk");

        List<Task> tasks = service.listTasks(null);

        assertEquals(2, tasks.size());
    }

    @Test
    void shouldListTasksByStatus() throws Exception {
        service.addTask("buy eggs");
        service.addTask("buy milk");

        service.markDone(1);

        List<Task> tasks = service.listTasks(Status.DONE);

        assertEquals(1, tasks.size());
        assertEquals(1, tasks.get(0).getId());
    }

    @Test
    void shouldMarkTaskAsInProgress() throws Exception {
        service.addTask("buy eggs");

        service.markInProgress(1);

        assertEquals(Status.IN_PROGRESS, repository.getTasks().get(0).getStatus());
    }

    @Test
    void shouldMarkTaskAsDone() throws Exception {
        service.addTask("buy eggs");

        service.markDone(1);

        assertEquals(Status.DONE, repository.getTasks().get(0).getStatus());
    }
}
