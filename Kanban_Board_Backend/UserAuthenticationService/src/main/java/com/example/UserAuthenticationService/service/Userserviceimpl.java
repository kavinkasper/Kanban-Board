package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.Domain.User;
import com.example.UserAuthenticationService.Exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.Exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.Exception.UserNotFoundException;
import com.example.UserAuthenticationService.Repository.Userrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Userserviceimpl implements Userservice {
    private Userrepository repos;

    @Autowired
    public Userserviceimpl(Userrepository repos) {
        this.repos = repos;
    }

    @Override
    public User saveuser(User user) throws UserAlreadyExistsException {
        if(repos.findById(user.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }
        return repos.save(user);
    }

    @Override
    public User getuserbyemailandpassword(String email, String password) throws InvalidCredentialsException {
        if (repos.findById(email).isEmpty()) {
            throw new InvalidCredentialsException();
        } else {
            return repos.findByEmailAndPassword(email, password);
        }
    }

    @Override
    public User updateuser(String email, User user) throws UserAlreadyExistsException {
        if(repos.findById(email).isEmpty()){
            throw new UserAlreadyExistsException();
        }
        User us=repos.findById(email).get();
        if(us.getEmail().equals(email)){
            us.setPassword(user.getPassword());
        }
        return repos.save(us);
    }

    @Override
    public Boolean deleteuser(String email) throws UserNotFoundException {
        Boolean value=false;
        if(repos.findById(email).isEmpty()){
            throw new UserNotFoundException();
        }
        else{
            repos.deleteById(email);
            value=true;
        }
        return value;
    }

    @Override
    public List<User> getallverifiedusers() {
        return repos.findAll();
    }

    @Override
    public List<String> getuseremail() {
       List<User>  us=repos.findAll();
       List<String> list=new ArrayList<>();
       for(User u:us){
           list.add(u.getEmail());
       }
        return list;
    }
}