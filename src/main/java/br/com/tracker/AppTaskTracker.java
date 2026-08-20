package br.com.tracker;

import java.io.IOException;

import br.com.tracker.controller.TaskController;
import br.com.tracker.repository.TaskRepository;
import br.com.tracker.service.TaskService;

public class AppTaskTracker {
    public static void main(String[] args) throws IOException {
        TaskRepository repository = new TaskRepository();
        TaskService service = new TaskService(repository);
        TaskController controller = new TaskController(service);

        controller.execute(args);
    }
}