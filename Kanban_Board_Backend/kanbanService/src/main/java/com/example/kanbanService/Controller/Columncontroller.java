package com.example.kanbanService.Controller;

import com.example.kanbanService.Domain.Columns;
import com.example.kanbanService.Service.Columnserviceimpl;
import com.example.kanbanService.exception.Boardsnotfound;
import com.example.kanbanService.exception.Columnnotfound;
import com.example.kanbanService.exception.UserNotFoundException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v2/kanban/column")
public class Columncontroller {
    private Columnserviceimpl service;

    @Autowired
    public Columncontroller(Columnserviceimpl service) {
        this.service = service;
    }

    @GetMapping("/getcolumns/{boardname}")
    public ResponseEntity getallcolumns(HttpServletRequest request, @PathVariable String boardname)
    throws UserNotFoundException,Boardsnotfound{
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity(service.getallcolumnsfromboard(emailId, boardname), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Boardsnotfound e) {
            throw new Boardsnotfound();
        }
    }

    @PostMapping("/addcolumns/{boardname}")
    public ResponseEntity savecolumntoboard(HttpServletRequest request, @PathVariable String boardname, @RequestBody Columns columns)
    throws UserNotFoundException,Boardsnotfound{
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity<>(service.savecolumnstoboards(emailId, boardname, columns), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Boardsnotfound e) {
            throw new Boardsnotfound();
        }
    }

    @DeleteMapping("/deletecloumn/{boardname}/{columnname}")
    public ResponseEntity deletecolumns(HttpServletRequest request, @PathVariable String boardname, @PathVariable String columnname)
    throws UserNotFoundException,Boardsnotfound,Columnnotfound{
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
//        service.deletecolumn(emailId,boardname,columnname);
            return new ResponseEntity<>(service.deletecolumn(emailId, boardname, columnname), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Boardsnotfound e) {
            throw new Boardsnotfound();
        } catch (Columnnotfound e) {
            throw new Columnnotfound();
        }
    }

    @PutMapping("/updatecolumns/{boardname}/{columnname}")
    public ResponseEntity updatecolumns(HttpServletRequest request, @PathVariable String boardname, @PathVariable String columnname, @RequestBody Columns column)
    throws UserNotFoundException,Columnnotfound,Boardsnotfound{
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity<>(service.updatecolumn(emailId, boardname, columnname, column), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Boardsnotfound e) {
            throw new Boardsnotfound();
        } catch (Columnnotfound e) {
            throw new Columnnotfound();
        }
    }


    @GetMapping("/getcolumnname/{boardname}")
    public ResponseEntity getallcolumn_names(HttpServletRequest request, @PathVariable String boardname) throws Columnnotfound {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity<>(service.getallcolumnnames(emailId, boardname), HttpStatus.CREATED);
        } catch (Columnnotfound e) {
            throw new Columnnotfound();
        }
    }

    @GetMapping("/getteammembersnames/{boardname}")
    public ResponseEntity getteammembersname(@PathVariable String boardname, HttpServletRequest request) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity<>(service.getteammembersnames(emailId, boardname), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
