package br.com.tracker.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.tracker.model.Status;

public class ControllerTest {
    FakeTaskService service;
    TaskController controller;

    @BeforeEach
    void setUp() {
        service = new FakeTaskService();
        controller = new TaskController(service);
    }

    // positive
    @Test
    void shouldCallAddTask() {
        controller.execute(new String[] { "add", "buy eggs" });

        assertTrue(service.addTaskCalled);
        assertEquals("buy eggs", service.receivedDescription);
    }

    @Test
    void shouldCallUpdate() {
        controller.execute(new String[] { "update", "1", "buy vegetables" });

        assertTrue(service.updateTaskCalled);
        assertEquals(1, service.receivedId);
        assertEquals("buy vegetables", service.receivedDescription);
    }

    @Test
    void shouldCallDelete() {
        controller.execute(new String[] { "delete", "1" });

        assertTrue(service.deleteTaskCalled);
        assertEquals(1, service.receivedId);
    }

    @Test
    void shouldCallAllList() {
        controller.execute(new String[] { "list" });

        assertTrue(service.listTasksCalled);
        assertNull(service.receivedStatus);
    }

    @Test
    void shouldCallListWithToDoFilter() {
        controller.execute(new String[] { "list", "todo" });

        assertTrue(service.listTasksCalled);
        assertEquals(Status.TODO, service.receivedStatus);
    }

    @Test
    void shouldCallListWithDoneFilter() {
        controller.execute(new String[] { "list", "done" });

        assertTrue(service.listTasksCalled);
        assertEquals(Status.DONE, service.receivedStatus);
    }

    @Test
    void shouldCallListWithInProgressFilter() {
        controller.execute(new String[] { "list", "in-progress" });

        assertTrue(service.listTasksCalled);
        assertEquals(Status.IN_PROGRESS, service.receivedStatus);
    }

    @Test
    void shouldCallMarkInProgress() {
        controller.execute(new String[] { "mark-in-progress", "1" });

        assertTrue(service.markInProgressCalled);
        assertEquals(1, service.receivedId);
    }

    @Test
    void shouldCallMarkDone() {
        controller.execute(new String[] { "mark-done", "1" });

        assertTrue(service.markDoneCalled);
        assertEquals(1, service.receivedId);
    }

    // negative
    @Test
    void shouldNotCallWithNoCommand() {
        controller.execute(new String[] {});

        assertFalse(service.addTaskCalled);
        assertFalse(service.updateTaskCalled);
        assertFalse(service.deleteTaskCalled);
        assertFalse(service.listTasksCalled);
        assertFalse(service.markInProgressCalled);
        assertFalse(service.markDoneCalled);
    }

    @Test
    void shouldNotCallServiceWithInvalidCommand() {
        controller.execute(new String[] { "unknown", "description" });

        assertFalse(service.addTaskCalled);
        assertFalse(service.updateTaskCalled);
        assertFalse(service.deleteTaskCalled);
        assertFalse(service.listTasksCalled);
        assertFalse(service.markInProgressCalled);
        assertFalse(service.markDoneCalled);
    }

    @Test
    void shouldNotCallAddTaskWithoutDescription() {
        controller.execute(new String[] { "add" });

        assertFalse(service.addTaskCalled);
    }

    @Test
    void shouldNotCallAddTaskWithExtraArgument() {
        controller.execute(new String[] { "add", "x", "extra" });

        assertFalse(service.addTaskCalled);
    }

    @Test
    void shouldNotCallUpdateTaskWithoutDescription() {
        controller.execute(new String[] { "update", "1" });

        assertFalse(service.updateTaskCalled);
    }

    @Test
    void shouldNotCallUpdateTaskWithExtraArgument() {
        controller.execute(new String[] { "update", "1", "description", "extra" });

        assertFalse(service.updateTaskCalled);
    }

    @Test
    void shouldNotCallDeleteTaskWithoutId() {
        controller.execute(new String[] { "delete" });

        assertFalse(service.deleteTaskCalled);
    }

    @Test
    void shouldNotCallDeleteTaskWithInvalidArgument() {
        controller.execute(new String[] { "delete", "x" });

        assertFalse(service.deleteTaskCalled);
    }

    @Test
    void shouldNotCallListWithInvalidArgument() {
        controller.execute(new String[] { "list", "unknown" });

        assertFalse(service.listTasksCalled);
    }

    @Test
    void shouldNotCallListWithExtraArgument() {
        controller.execute(new String[] { "list", "done", "extra" });

        assertFalse(service.listTasksCalled);
    }

    @Test
    void shouldNotCallMarkDoneWithoutId() {
        controller.execute(new String[] { "mark-done" });

        assertFalse(service.markDoneCalled);
    }

    @Test
    void shouldNotCallMarkInProgressWithoutId() {
        controller.execute(new String[] { "mark-in-progress" });

        assertFalse(service.markInProgressCalled);
    }

    @Test
    void shouldNotCallMarkDoneWithInvalidId() {
        controller.execute(new String[] { "mark-done", "x" });

        assertFalse(service.markDoneCalled);
    }

    @Test
    void shouldNotCallMarkInProgressWithInvalidId() {
        controller.execute(new String[] { "mark-in-progress", "x" });

        assertFalse(service.markInProgressCalled);
    }
}
