package com.example.kanbanService.Controller;

import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.Service.Userserviceimpl;
import com.example.kanbanService.exception.Passwordmissmatch;
import com.example.kanbanService.exception.UserAlreadyExistsException;
import com.example.kanbanService.exception.UserNotFoundException;
import io.jsonwebtoken.Claims;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping("api/v2")
public class Usercontroller {
    private Userserviceimpl service;

    @Autowired
    private RabbitTemplate rabbitTemplate;
@Autowired
    public Usercontroller(Userserviceimpl service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity saveuserdetails(@RequestBody Kanban user)throws UserAlreadyExistsException {
    try {
        Kanban registeredUser = service.saveuser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }catch (UserAlreadyExistsException e){
        throw new UserAlreadyExistsException();
    }
    }

    @PutMapping("/kanban/update")
    public ResponseEntity updateuserdetails(@RequestBody Kanban user, HttpServletRequest request) throws UserNotFoundException,Passwordmissmatch {
        try {
            Kanban updatedUser = service.updateuserdetails(user);
            return new ResponseEntity(updatedUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Passwordmissmatch e) {
            throw new Passwordmissmatch();
        }
    }


    @DeleteMapping("/kanban/delete")
    public ResponseEntity deletedetails(HttpServletRequest request)throws UserNotFoundException{
    try {
        Claims claims=(Claims) request.getAttribute("claims");
        String emailid=claims.getSubject();
        System.out.println("Email ID: " + emailid);
        service.deleteuser(emailid);
        return new ResponseEntity("DELETED", HttpStatus.OK);
    }catch (UserNotFoundException e){
        throw new UserNotFoundException();
    }
    }

    @GetMapping("/kanban/getusers")
    public ResponseEntity getallusers(HttpServletRequest request) {
        try {
            Claims claims=(Claims) request.getAttribute("claims");
            String emailID=claims.getSubject();
            return new ResponseEntity<>(service.getusers(emailID), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
