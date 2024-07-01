package com.lmptech.intune.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document("Chats")
public class ChatModel {
    @MongoId
    String Id;
    String roomId;
    String name;

    @DBRef
    List<UserModel> members;

    @DBRef
    List<MessageModel> messages;
}
