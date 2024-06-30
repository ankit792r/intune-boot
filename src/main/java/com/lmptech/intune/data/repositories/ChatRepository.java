package com.lmptech.intune.data.repositories;

import com.lmptech.intune.data.models.ChatModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends MongoRepository<ChatModel, String> {
}
