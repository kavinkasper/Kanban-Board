package com.example.UserAuthenticationService.Controller;

import com.example.UserAuthenticationService.Domain.User;
import com.example.UserAuthenticationService.Exception.InvalidCredentialsException;
import com.example.UserAuthenticationService.Exception.UserAlreadyExistsException;
import com.example.UserAuthenticationService.Exception.UserNotFoundException;
import com.example.UserAuthenticationService.Security.SecurityTokenGenerator;
import com.example.UserAuthenticationService.service.Userserviceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class Usercontroller {
    private Userserviceimpl service;
    private SecurityTokenGenerator tokengenerator;
    @Autowired
    public Usercontroller(Userserviceimpl service, SecurityTokenGenerator tokengenerator) {
        this.service = service;
        this.tokengenerator = tokengenerator;
    }

    @PostMapping("/saveuser")
    public ResponseEntity saveusers(@RequestBody User user)throws UserAlreadyExistsException {
        return new ResponseEntity<>(service.saveuser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity loginuser(@RequestBody User user)throws InvalidCredentialsException{
        if(service.getuserbyemailandpassword(user.getEmail(), user.getPassword())==null){
            throw new InvalidCredentialsException();
        }
        String token=tokengenerator.createToken(user);
        return new ResponseEntity(token,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateuser(String email,@RequestBody User user)throws UserAlreadyExistsException{
        return new ResponseEntity<>(service.updateuser(email,user), HttpStatus.CREATED);
    }

    @GetMapping("/getallusers")
    public ResponseEntity getusers(){
        return new ResponseEntity<>(service.getallverifiedusers(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteuser")
    public ResponseEntity deleteuser(String email)throws UserNotFoundException {
        return new ResponseEntity<>(service.deleteuser(email),HttpStatus.OK);
    }

    @GetMapping("/getuseremail")
    public ResponseEntity getuseremail(){
        return new ResponseEntity<>(service.getuseremail(),HttpStatus.OK);
    }

}
