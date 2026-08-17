package br.com.tracker.service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import br.com.tracker.model.Task;
import br.com.tracker.repository.TaskRepository;

public class FakeTaskRepository extends TaskRepository {

    private List<Task> tasks = new ArrayList<>();

    public FakeTaskRepository() throws IOException {
        super(Files.createTempFile("fake-task-repository-", ".json"));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public List<Task> findAll() throws IOException {
        return tasks;
    }

    @Override
    public void saveAll(List<Task> tasks) throws IOException {
        this.tasks = tasks;
    }
}
