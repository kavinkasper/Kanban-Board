package com.example.kanbanService.Controller;

import com.example.kanbanService.Domain.Boards;
import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.Domain.User;
import com.example.kanbanService.Service.Boardsserviceimpl;
import com.example.kanbanService.exception.Boardsnotfound;
import com.example.kanbanService.exception.Moretanthreetask;
import com.example.kanbanService.exception.TeamMemberAlreadyExists;
import com.example.kanbanService.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/api/v2")
public class Boardcontroller {
    private Boardsserviceimpl service;
    private ResponseEntity response;
    @Autowired
    private RabbitTemplate rabbitTemplate;
@Autowired
    public Boardcontroller(Boardsserviceimpl service) {
        this.service = service;
    }

@GetMapping("/kanban/getallboards")
public ResponseEntity<List<Boards>> getAllBoards(HttpServletRequest request) throws Boardsnotfound {
    try {
        Claims claims=(Claims) request.getAttribute("claims");
        String emailid=claims.getSubject();
        List<Boards> boards = service.getallboards(emailid);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    } catch (Boardsnotfound e) {
        throw new Boardsnotfound();
    }
}
@PostMapping("/kanban/saveboards")
public ResponseEntity saveBoards(@RequestBody Boards boards,HttpServletRequest request)
        throws Boardsnotfound,UserNotFoundException,TeamMemberAlreadyExists,Moretanthreetask {
    try {
        Claims claims=(Claims) request.getAttribute("claims");
        String emailid=claims.getSubject();
        System.out.println("Email ID: " + emailid);
        Kanban bordetails = service.saveboards(emailid,boards);
        return new ResponseEntity<>(bordetails, HttpStatus.CREATED);
    } catch (Boardsnotfound e) {
        throw new Boardsnotfound();
    } catch (UserNotFoundException e) {
        throw new UserNotFoundException();
    } catch (TeamMemberAlreadyExists e) {
        throw new TeamMemberAlreadyExists();
    } catch (Moretanthreetask e) {
        throw new Moretanthreetask();
    }
}

@PutMapping("/kanban/updatingboard/{boardname}")
    public ResponseEntity updatingboard(@PathVariable String boardname,@RequestBody Boards board,HttpServletRequest request)
        throws UserNotFoundException,Boardsnotfound,TeamMemberAlreadyExists,Moretanthreetask{
    try{
        Claims claims=(Claims) request.getAttribute("claims");
        String emailid=claims.getSubject();
        System.out.println("Email ID: " + emailid);
        boolean updatedboards=service.updateboard(emailid,boardname,board);
        return new ResponseEntity<>(updatedboards, HttpStatus.CREATED);
    } catch (UserNotFoundException e) {
        throw new UserNotFoundException();
    } catch (Boardsnotfound e) {
        throw new Boardsnotfound();
    } catch (TeamMemberAlreadyExists e) {
        throw new TeamMemberAlreadyExists();
    } catch (Moretanthreetask e) {
        throw new Moretanthreetask();
    }
}

@DeleteMapping("/kanban/deleteboard/{boardname}")
    public ResponseEntity deletingboards(@PathVariable String boardname,HttpServletRequest request)
throws UserNotFoundException,Boardsnotfound{
    try{
        Claims claims=(Claims) request.getAttribute("claims");
        String emailid=claims.getSubject();
        System.out.println("Email: "+emailid);
        return new ResponseEntity(service.Deleteboard(emailid,boardname),HttpStatus.OK);
    }catch (UserNotFoundException e) {
        throw new UserNotFoundException();
    } catch (Boardsnotfound e) {
        throw new Boardsnotfound();
    }
}

@PostMapping("/kanban/addteam_members/{boardname}/{memberEmail}")
    public ResponseEntity addteammember(@PathVariable String boardname,@PathVariable String memberEmail,HttpServletRequest request)
throws UserNotFoundException,Boardsnotfound,Moretanthreetask,JsonProcessingException,TeamMemberAlreadyExists{
    try{
        Claims claims=(Claims) request.getAttribute("claims");
        String emailid=claims.getSubject();
        System.out.println(emailid);
        System.out.println(memberEmail);
        boolean k=service.addMemberToBoardByEmail(boardname,memberEmail,emailid);

        String message = memberEmail+" you have been added to Board :"+boardname+" by the Admin: "+emailid;
        List<String> mm=new ArrayList<>();
        mm.add(message);
        User user=new User();
        user.setEmail(memberEmail);
        user.setMessage(mm);
        ObjectMapper objectMapper = new ObjectMapper();
        String messagee = objectMapper.writeValueAsString(user);
        rabbitTemplate.convertAndSend("kavinexchange", "kavinroutingkey1", messagee);

        return new ResponseEntity<>(k,HttpStatus.CREATED);
    } catch (UserNotFoundException e) {
        throw new UserNotFoundException();
    } catch (Boardsnotfound e) {
        throw new Boardsnotfound();
    } catch (TeamMemberAlreadyExists memberfound) {
        throw new TeamMemberAlreadyExists();
    }
}


@GetMapping("/kanban/getboardnames")
    public ResponseEntity getnamesofboards(HttpServletRequest request)throws Boardsnotfound{
    try {
        Claims claims = (Claims) request.getAttribute("claims");
        String emailid = claims.getSubject();
        System.out.println(emailid);
        return new ResponseEntity<>(service.getallboardnames(emailid),HttpStatus.OK);
    }catch (Boardsnotfound e){
        throw new Boardsnotfound();
    }
}

}

