package com.example.kanbanService.Service;

import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.Proxy.Userproxy;
import com.example.kanbanService.exception.Passwordmissmatch;
import com.example.kanbanService.exception.UserAlreadyExistsException;
import com.example.kanbanService.exception.UserNotFoundException;
import com.example.kanbanService.repository.Kanbanrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class Userserviceimpl implements Userservice {
    private Kanbanrepository repos;
    private Userproxy proxy;
    List<String> messages=new ArrayList<>();


    @Autowired
    public Userserviceimpl(Kanbanrepository repos, Userproxy proxy) {
        this.repos = repos;
        this.proxy = proxy;
    }

    @Override
    public Kanban saveuser(Kanban user) throws UserAlreadyExistsException {
        if (repos.findById(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        Kanban saveusers = repos.save(user);
        if (!(saveusers.getEmail().isEmpty())) {
            ResponseEntity<Kanban> r = proxy.saveusers(user);
            System.out.println(r.getBody());
        }
        return saveusers;
    }

    @Override
    public Kanban updateuserdetails(Kanban user) throws UserNotFoundException, Passwordmissmatch {
        Optional<Kanban> existingUser = repos.findById(user.getEmail());

        if (existingUser.isPresent()) {
            Kanban userdetail = existingUser.get();
            userdetail.setUsername(user.getUsername());
            userdetail.setMobilenumber(user.getMobilenumber());
            userdetail.setPassword(user.getPassword());
            userdetail.setConfirmpassword(user.getConfirmpassword());
            if(userdetail.getPassword().equals(userdetail.getConfirmpassword())) {
                repos.save(userdetail);
                proxy.updateuser(userdetail.getEmail(), userdetail);
            }else{
                throw new Passwordmissmatch();
            }
            return userdetail;
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public boolean deleteuser(String email) throws UserNotFoundException {
        List<Kanban> list = repos.findAll();

        for (Kanban user : list) {
            if (user.getEmail().equals(email)) {
                repos.delete(user);
                proxy.deleteuser(user.getEmail());
                System.out.println("HIII");
                return true;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public Kanban getusers(String email) {
        return repos.findById(email).get();
    }

}
