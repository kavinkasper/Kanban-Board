package com.example.Notificationservice.Repository;

import com.example.Notificationservice.Domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface notificationrepos extends MongoRepository<User,String> {
}
