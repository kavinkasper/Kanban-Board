package com.example.Notificationservice.Service;

import com.example.Notificationservice.Domain.User;
import com.example.Notificationservice.Repository.notificationrepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService implements NotificationServiceimpl {
   private User us=new User();


    private notificationrepos repos;
@Autowired
    public NotificationService(notificationrepos repos) {
        this.repos = repos;
    }

    @Override
    public void savedmessages(User message) {
        String user = message.getEmail();
        List<String> userMessages = message.getMessage();

        User Updateuser = repos.findById(user).orElse(null);

        if (Updateuser == null) {
            Updateuser = new User();
            Updateuser.setEmail(user);
            Updateuser.setMessage(new ArrayList<>());
        }

        Updateuser.getMessage().addAll(userMessages);
        repos.save(Updateuser);
    }

    @Override
    public void savedtaskmessage(User msg) {
    String user=msg.getEmail();
    List<String> usermessage=msg.getMessage();

        User Updateuser = repos.findById(user).orElse(null);
        if (Updateuser == null) {
            Updateuser = new User();
            Updateuser.setEmail(user);
            Updateuser.setMessage(new ArrayList<>());
        }

        Updateuser.getMessage().addAll(usermessage);
        repos.save(Updateuser);
    }

    @Override
    public List<User> getMessagesGroupedByUser() {
        return repos.findAll();
    }


    @Override
    public List<String> getmessagesbyemail(String email) {
        List<String> messages = repos.findById(email).get().getMessage();

        if (messages == null) {
            return new ArrayList<>();
        } else {
            return messages;
        }
    }

}


