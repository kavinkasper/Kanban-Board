package com.example.kanbanService.Domain;
import org.springframework.data.annotation.Id;

import java.util.List;

public class Columns {
    @Id
private String column_name;
private List<Tasks> tasks;

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Columns{" +
                "column_name='" + column_name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
