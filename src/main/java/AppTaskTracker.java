import br.com.tracker.controller.TaskController;

public class AppTaskTracker {
    public static void main(String[] args) {
        TaskController taskController = new TaskController(null);
        taskController.execute(args);
    }
}
