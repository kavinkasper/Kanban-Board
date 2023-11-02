package com.example.kanbanService.repository;

import com.example.kanbanService.Domain.Kanban;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface Kanbanrepository extends MongoRepository<Kanban,String> {
}
