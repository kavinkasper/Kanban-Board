package com.example.Notificationservice.Controller;

import com.example.Notificationservice.Service.NotificationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v3")
public class Notificationcontroller {

    private NotificationService service;
@Autowired
    public Notificationcontroller(NotificationService service) {
        this.service = service;
    }

    @GetMapping("/kanban/getemailmsg")
    public ResponseEntity getmsgbyemail(HttpServletRequest request){
        Claims claims=(Claims) request.getAttribute("claims");
        String emailid=claims.getSubject();
        return new ResponseEntity(service.getmessagesbyemail(emailid), HttpStatus.OK);
    }


}
