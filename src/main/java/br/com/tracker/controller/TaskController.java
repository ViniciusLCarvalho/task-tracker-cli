package br.com.tracker.controller;

import br.com.tracker.service.TaskService;

public class TaskController {
    public void executar(String[] args) {
        TaskService ts = new TaskService();
        
        switch (args[0]) {
            case "add":
                ts.addTask(args[1]);
                break;
            case "update":
                ts.updateTask(args[1], args[2]);
                break;
            case "delete":
                ts.deleteTask(args[1]);
                break;
            case "list":
                ts.listTasks(args[1]);
                break;
            case "mark-in-progress":
                ts.markInProgress(args[1]);
                break;
            case "mark-done":
                ts.markDone(args[1]);
                break;
            default:
                System.out.println("Unknown command");
                break;
        }
    }
}
