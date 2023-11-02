package com.example.kanbanService.Domain;

import java.util.List;

public class User {
    private String email;
    private List<String> message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
