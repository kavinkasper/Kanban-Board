package com.example.kanbanService.Service;

import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.exception.Passwordmissmatch;
import com.example.kanbanService.exception.UserAlreadyExistsException;
import com.example.kanbanService.exception.UserNotFoundException;

import java.util.List;

public interface Userservice {
    Kanban saveuser(Kanban user) throws UserAlreadyExistsException;
    Kanban updateuserdetails(Kanban user) throws UserNotFoundException, Passwordmissmatch;
    boolean deleteuser(String email) throws UserNotFoundException;

    Kanban getusers(String email);
}
