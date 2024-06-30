package com.lmptech.intune.services;

import com.lmptech.intune.data.models.ChatModel;
import com.lmptech.intune.data.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ChatModel> getAllChats() {
        return mongoTemplate.findAll(ChatModel.class);
    }
}
