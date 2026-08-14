package br.com.tracker.controller;

import java.io.IOException;
import java.util.List;

import br.com.tracker.model.Status;
import br.com.tracker.model.Task;
import br.com.tracker.service.TaskService;

public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    public void execute(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: task-cli <command>");
            return;
        }

        try {
            switch (args[0]) {
                case "add":
                    if (args.length != 2) {
                        System.out.println("Usage: task-cli add \"description\"");
                        return;
                    }
                    taskService.addTask(args[1]);
                    break;
                case "update":
                    if (args.length != 3) {
                        System.out.println("Usage: task-cli update id \"description\"");
                        return;
                    }
                    taskService.updateTask(Integer.parseInt(args[1]), args[2]);
                    break;
                case "delete":
                    if (args.length != 2) {
                        System.out.println("Usage: task-cli delete id");
                        return;
                    }
                    taskService.deleteTask(Integer.parseInt(args[1]));
                    break;
                case "list":
                    Status status = null;
                    List<Task> tasks;
                    
                    if (args.length > 2) {
                        System.out.println("Usage: task-cli list [done|todo|in-progress]");
                        return;
                    }

                    if (args.length > 1) {
                        switch (args[1]) {
                            case "done":
                                status = Status.DONE;
                                break;
                            case "todo":
                                status = Status.TODO;
                                break;
                            case "in-progress":
                                status = Status.IN_PROGRESS;
                                break;
                            default:
                                System.out.println("Unknown status");
                                return;
                        }
                    }
                    tasks = taskService.listTasks(status);
                    for (Task task : tasks) {
                        System.out.println(task.toString());
                    }
                    break;
                case "mark-in-progress":
                    if (args.length != 2) {
                        System.out.println("Usage: task-cli mark-in-progress id");
                        return;
                    }
                    taskService.markInProgress(Integer.parseInt(args[1]));
                    break;
                case "mark-done":
                    if (args.length != 2) {
                        System.out.println("Usage: task-cli mark-done id");
                        return;
                    }
                    taskService.markDone(Integer.parseInt(args[1]));
                    break;
                default:
                    System.out.println("Unknown command");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Task ID must be a number.");
        } catch (IOException e){
            System.out.println("Error acessing tasks file.");
        }
    }
}
