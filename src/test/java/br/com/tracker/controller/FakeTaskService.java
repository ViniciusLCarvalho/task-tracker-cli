package br.com.tracker.controller;

import java.io.IOException;
import java.util.List;

import br.com.tracker.model.Status;
import br.com.tracker.model.Task;
import br.com.tracker.service.TaskService;

/**
 * FakeTaskService
 */
public class FakeTaskService extends TaskService {
    int receivedId;
    String receivedDescription;
    Status receivedStatus;

    boolean addTaskCalled;
    boolean updateTaskCalled;
    boolean deleteTaskCalled;
    boolean listTasksCalled;
    boolean markInProgressCalled;
    boolean markDoneCalled;

    public FakeTaskService() {
        super(null);
    }

    @Override
    public void addTask(String desc) throws IOException {
        addTaskCalled = true;
        receivedDescription = desc;
    }

    @Override
    public void updateTask(int id, String desc) throws IOException {
        updateTaskCalled = true;
        receivedId = id;
        receivedDescription = desc;
    }

    @Override
    public void deleteTask(int id) throws IOException {
        deleteTaskCalled = true;
        receivedId = id;
    }

    @Override
    public List<Task> listTasks(Status status) throws IOException {
        listTasksCalled = true;
        receivedStatus = status;
        return List.of();
    }

    @Override
    public void markDone(int id) throws IOException {
        markDoneCalled = true;
        receivedId = id;
    }

    @Override
    public void markInProgress(int id) throws IOException {
        markInProgressCalled = true;
        receivedId = id;
    }
}
