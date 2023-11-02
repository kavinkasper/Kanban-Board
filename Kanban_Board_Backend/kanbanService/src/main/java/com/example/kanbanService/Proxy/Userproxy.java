package com.example.kanbanService.Proxy;

import com.example.kanbanService.Domain.Kanban;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user-authentication-service",url = "localhost:8087")
public interface Userproxy {
    @PostMapping("api/v1/saveuser")
    public ResponseEntity saveusers(@RequestBody Kanban user);

    @PutMapping("api/v1/update")
    public ResponseEntity updateuser(@RequestParam("email")String email, @RequestBody Kanban user);

    @DeleteMapping("api/v1/deleteuser")
    public ResponseEntity deleteuser(@RequestParam("email")String email);

    }
