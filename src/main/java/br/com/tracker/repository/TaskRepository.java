package br.com.tracker.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.tracker.model.Status;
import br.com.tracker.model.Task;

public class TaskRepository {
    private final Path filePath;

    public TaskRepository() throws IOException {
        this(Paths.get("tasks.json"));
    }

    public TaskRepository(Path filePath) throws IOException {
        this.filePath = filePath;
        createFileIfNotExists();
    }

    private void createFileIfNotExists() throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
            Files.writeString(filePath, "[]");
        }
    }

    private String readFile() throws IOException {
        return Files.readString(filePath);
    }

    private void writeFile(String content) throws IOException {
        Files.writeString(filePath, content);
    }

    private String taskToJson(Task task) {
        StringBuilder json = new StringBuilder();
        json.append("   {\n");
        json.append("       \"id\": ")
                .append(task.getId())
                .append(",\n");
        json.append("       \"description\": \"")
                .append(task.getDescription())
                .append("\",\n");
        json.append("       \"status\": \"")
                .append(task.getStatus())
                .append("\",\n");
        json.append("       \"createdAt\": \"")
                .append(task.getCreatedAt())
                .append("\",\n");
        json.append("       \"updatedAt\": \"")
                .append(task.getUpdatedAt())
                .append("\"\n");
        json.append("   }");

        return json.toString();
    }

    private Task jsonToTask(String json) {
        int id = Integer.parseInt(getValue(json, "id"));
        String description = getValue(json, "description");
        Status status = Status.fromValue(getValue(json, "status"));
        LocalDateTime createdAt = LocalDateTime.parse(getValue(json, "createdAt"));
        LocalDateTime updatedAt = LocalDateTime.parse(getValue(json, "updatedAt"));

        return new Task(id, description, status, createdAt, updatedAt);
    }

    private String getValue(String json, String key) {
        String[] lines = json.split("\n");

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("\"" + key + "\"")) {
                String value = line.substring(line.indexOf(":") + 1).trim();

                if (value.endsWith(",")) {
                    value = value.substring(0, value.length() - 1);
                }

                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length() - 1);
                }

                return value;
            }
        }
        throw new IllegalArgumentException("Campo não encontrado: " + key);
    }

    public List<Task> findAll() throws IOException {
        List<Task> tasks = new ArrayList<>();
        String json = readFile().trim();

        if (json.equals("[]")){
            return tasks;
        }

        StringBuilder object = new StringBuilder();
        boolean readingObject = false;

        for (String line : json.split("\n")) {
            line = line.trim();

            if (line.startsWith("{")) {
                readingObject = true;
                object.setLength(0);
            }

            if (readingObject) {
                object.append(line).append("\n");
            }

            if (line.startsWith("}")) {
                readingObject = false;
                tasks.add(jsonToTask(object.toString()));
            }
        }
        return tasks;
    }

    public void saveAll(List<Task> tasks) throws IOException {
        StringBuilder json = new StringBuilder();

        json.append("[\n");

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            json.append(taskToJson(task));

            if (i < tasks.size() - 1) {
                json.append(",\n");
            }
        }

        json.append("\n]");

        writeFile(json.toString());
    }
}
