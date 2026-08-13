package br.com.tracker.service;

import java.io.IOException;
import java.util.List;

import br.com.tracker.model.Status;
import br.com.tracker.model.Task;
import br.com.tracker.repository.TaskRepository;

public class TaskService {

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public void addTask(String desc) throws IOException {
        List<Task> tasks = repository.findAll();
        int nextId = 1;

        for (Task task : tasks) {
            if (task.getId() >= nextId) {
                nextId = task.getId() + 1;
            }
        }
        Task task = new Task(nextId, desc);
        tasks.add(task);
        repository.saveAll(tasks);
    }

    public void updateTask(int id, String desc) throws IOException {
        List<Task> tasks = repository.findAll();

        for (Task task : tasks) {
            if (task.getId() == id) {
                task.setDescription(desc);
                break;
            }
        }

        repository.saveAll(tasks);
    }

    public void deleteTask(int id) throws IOException {
        List<Task> tasks = repository.findAll();

        tasks.removeIf(task -> task.getId() == id);

        repository.saveAll(tasks);
    }

    public List<Task> listTasks(Status status) throws IOException {
        List<Task> tasks = repository.findAll();

        if (status == null) {
            return tasks;
        }

        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .toList();
    }

    public void markInProgress(int id) throws IOException {
        List<Task> tasks = repository.findAll();

        Task task = findTaskById(tasks, id);

        if (task != null) {
            task.setStatus(Status.IN_PROGRESS);
        }

        repository.saveAll(tasks);
    }

    public void markDone(int id) throws IOException {
        List<Task> tasks = repository.findAll();

        Task task = findTaskById(tasks, id);

        if (task != null) {
            task.setStatus(Status.DONE);
        }

        repository.saveAll(tasks);
    }

    private Task findTaskById(List<Task> tasks, int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }
}
