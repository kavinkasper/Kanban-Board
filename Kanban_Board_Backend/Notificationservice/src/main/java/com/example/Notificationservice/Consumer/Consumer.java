package com.example.Notificationservice.Consumer;

import com.example.Notificationservice.Domain.User;
import com.example.Notificationservice.Service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {

    private NotificationService service;
@Autowired
    public Consumer(NotificationService service) {
        this.service = service;
    }

    @RabbitListener(queues = "kavinqueee")
    public void Consumer(String jsonfile) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(jsonfile, User.class);
            service.savedmessages(user);
            System.out.println("Message: "+user.getMessage().get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "kavinque")
    public void Consumer1(String jsonfile) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(jsonfile, User.class);
            service.savedtaskmessage(user);
            System.out.println("Message: "+user.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
