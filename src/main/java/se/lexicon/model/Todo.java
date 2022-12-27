package se.lexicon.model;

import lombok.*;

import java.time.LocalDate;

@Data
public class Todo {
    private final int todoId;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private int assigneeId;

    public Todo(int todoId, String title, String description, LocalDate deadLine, boolean done, int assigneeId) {
        this.todoId = todoId;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
        this.assigneeId = assigneeId;
    }
}
