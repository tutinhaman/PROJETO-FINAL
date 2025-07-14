package com.taskmanager.model;

import com.taskmanager.model.StatusTarefa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.UUID;

public class Tarefa {
    private String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private StatusTarefa status;

    public Tarefa() {
    }

    public Tarefa(String title, String description, LocalDate dueDate, StatusTarefa status) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    @JsonCreator
    public Tarefa(@JsonProperty("id") String id, 
                @JsonProperty("title") String title, 
                @JsonProperty("description") String description, 
                @JsonProperty("dueDate") LocalDate dueDate, 
                @JsonProperty("status") StatusTarefa status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tarefa{" +
               "id=\'" + id + '\'' +
               ", titulo=\'" + title + '\'' +
               ", descricao=\'" + description + '\'' +
               ", dataVencimento=" + dueDate +
               ", status=" + status +
               '}';
    }
}

