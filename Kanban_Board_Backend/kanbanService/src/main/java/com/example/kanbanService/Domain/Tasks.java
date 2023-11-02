package com.example.kanbanService.Domain;

import org.springframework.data.annotation.Id;
import java.util.Date;

public class Tasks {
    @Id
    private String name;
    private String description;
    private String priority;
    private Date startDate;
    private Date dueDate;
    private String assigneeEmail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getAssigneeEmail() {
        return assigneeEmail;
    }

    public void setAssigneeEmail(String assigneeEmail) {
        this.assigneeEmail = assigneeEmail;
    }

    @Override
    public String toString() {
        return "Tasks{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", startDate=" + startDate +
                ", dueDate=" + dueDate +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                '}';
    }
}
