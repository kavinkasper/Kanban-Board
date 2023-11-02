package com.example.kanbanService.Controller;

import com.example.kanbanService.Domain.Kanban;
import com.example.kanbanService.Domain.Tasks;
import com.example.kanbanService.Domain.User;
import com.example.kanbanService.Service.Taskserviceimpl;
import com.example.kanbanService.exception.*;
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
@RequestMapping("/api/v2/kanban/task")
public class Taskcontroller {
    private Taskserviceimpl services;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public Taskcontroller(Taskserviceimpl services) {
        this.services = services;
    }

    @GetMapping("/gettask/{boardname}/{columnname}/{taskname}")
    public ResponseEntity gettaskbytaskname(HttpServletRequest request, @PathVariable String boardname, @PathVariable String columnname, @PathVariable String taskname)
    throws UserNotFoundException,Tasknotfound{
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailid = claims.getSubject();
            return new ResponseEntity(services.getTaskByTaskName(emailid, boardname, columnname, taskname), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Tasknotfound e) {
            throw new Tasknotfound();
        }
    }

    @PostMapping("/savetask/{column_name}")
    public ResponseEntity saveTasks(HttpServletRequest request, @PathVariable String column_name, @RequestBody Tasks task)
            throws UserNotFoundException, Columnnotfound, Taskalreadyexists,Moretanthreetask {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();

            String message = "Hi " + task.getAssigneeEmail() + ", you have been assigned a Task by Admin "+emailId;
            List<String> m = new ArrayList<>();
            m.add(message);
            User user = new User();
            user.setEmail(task.getAssigneeEmail());
            user.setMessage(m);
            ObjectMapper objectMapper = new ObjectMapper();
            String messagee = objectMapper.writeValueAsString(user);
            rabbitTemplate.convertAndSend("kavinexchange", "kavinroutingkey", messagee);

            return new ResponseEntity<>(services.savetasktocolumn(emailId, column_name, task), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Taskalreadyexists e) {
            throw new Taskalreadyexists();
        } catch (Columnnotfound e) {
            throw new Columnnotfound();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Moretanthreetask e) {
            throw new Moretanthreetask();
        }
    }

    @DeleteMapping("/deletetasks/{boardname}/{ColumnName}/{TaskName}")
    public ResponseEntity deletetask(HttpServletRequest request, @PathVariable String boardname, @PathVariable String ColumnName, @PathVariable String TaskName)
    throws UserNotFoundException,Tasknotfound,Columnnotfound{
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            services.deletetaskfromcolumn(emailId, boardname, ColumnName, TaskName);
            return new ResponseEntity(services.deletetaskfromcolumn(emailId, boardname, ColumnName, TaskName), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Tasknotfound e) {
            throw new Tasknotfound();
        } catch (Columnnotfound e) {
            throw new Columnnotfound();
        }
    }

    @PutMapping("/updatetask/{boardName}/{ColumnName}/{TaskName}")
    public ResponseEntity Updatethetask(HttpServletRequest request, @PathVariable String boardName,
                                        @PathVariable String ColumnName, @PathVariable String TaskName, @RequestBody Tasks task)
            throws UserNotFoundException,Tasknotfound,Columnnotfound{
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity<>(services.UpdateTask(emailId, boardName, ColumnName, TaskName, task), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Tasknotfound e) {
            throw new Tasknotfound();
        } catch (Columnnotfound e) {
            throw new Columnnotfound();
        }
    }

    @PostMapping("/addtasktouser/{column_name}")
    public ResponseEntity addtasktouser(@PathVariable String column_name, @RequestBody Tasks task, HttpServletRequest request)
            throws UserNotFoundException, Columnnotfound, Moretanthreetask {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            Kanban k = services.addtasktousercolumn(column_name, task);

//            String message = "Hi " + task.getAssigneeEmail() + ", you have been assigned a task by " + emailId;
//            List<String> m = new ArrayList<>();
//            m.add(message);
//            User user = new User();
//            user.setEmail(task.getAssigneeEmail());
//            user.setMessage(m);
//            ObjectMapper objectMapper = new ObjectMapper();
//            String messagee = objectMapper.writeValueAsString(user);
//            rabbitTemplate.convertAndSend("kavinexchange", "kavinroutingkey", messagee);

            return new ResponseEntity<>(k, HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        } catch (Columnnotfound e) {
            throw new Columnnotfound();
        } catch (Moretanthreetask e) {
            throw new Moretanthreetask();
        }
    }

    @GetMapping("/getalltasks/{columname}")
    public ResponseEntity getalltasks(HttpServletRequest request, @PathVariable String columname) {
        try {
            Claims claims = (Claims) request.getAttribute("claims");
            String emailId = claims.getSubject();
            return new ResponseEntity<>(services.getalltasks(emailId, columname), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @PostMapping("/updatingdraganddrop/{boardname}/{columnname}")
    public ResponseEntity updatingusingdraganddrop(HttpServletRequest request,@PathVariable String boardname,@PathVariable String columnname,@RequestBody Tasks tasks){
        Claims claims = (Claims) request.getAttribute("claims");
        String emailId = claims.getSubject();
        return new ResponseEntity<>(services.updatingtaskbydraganddrop(emailId,boardname,columnname,tasks),HttpStatus.CREATED);
    }
}
