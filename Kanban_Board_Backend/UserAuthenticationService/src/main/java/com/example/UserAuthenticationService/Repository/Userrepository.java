package com.example.UserAuthenticationService.Repository;

import com.example.UserAuthenticationService.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Userrepository extends JpaRepository<User,String> {
    User findByEmailAndPassword(String email, String password);}
