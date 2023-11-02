package com.example.kanbanService.Domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;

@Document
public class Kanban {
    @Id
    private String email;
    private String username;
    private String password;
    private String confirmpassword;
    private long mobilenumber;
    private List<Boards> boards;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public long getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(long mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public List<Boards> getBoards() {
        return boards;
    }

    public void setBoards(List<Boards> boards) {
        this.boards = boards;
    }

    @Override
    public String toString() {
        return "Kanban{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmpassword='" + confirmpassword + '\'' +
                ", mobilenumber=" + mobilenumber +
                ", boards=" + boards +
                '}';
    }
}
