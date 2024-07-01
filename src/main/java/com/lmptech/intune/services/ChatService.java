package com.lmptech.intune.services;

import com.lmptech.intune.data.models.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ChatModel> getAllChats() {
        return mongoTemplate.findAll(ChatModel.class);
    }
}
