package com.example.UserAuthenticationService.service;

import com.example.UserAuthenticationService.Domain.User;
import com.example.UserAuthenticationService.Exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.Exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.Exception.UserNotFoundException;

import java.util.List;

public interface Userservice {
    User saveuser(User user) throws UserAlreadyExistsException;
    User getuserbyemailandpassword(String email,String password) throws InvalidCredentialsException;

    User updateuser(String email,User user) throws UserAlreadyExistsException;

    Boolean deleteuser(String email)throws UserNotFoundException;

    List<User> getallverifiedusers();

    List<String> getuseremail();
}
