package com.example.Notificationservice.Service;

import com.example.Notificationservice.Domain.User;

import java.util.List;
import java.util.Map;

public interface NotificationServiceimpl {
    void savedmessages(User message);

    void savedtaskmessage(User msg);

    List<User> getMessagesGroupedByUser();

//   List<User> getMessagesGroupedByUserfortask();


    List<String> getmessagesbyemail(String email);

//    List<String> getmessagesbyemailtask(String email);

}
