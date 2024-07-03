package com.lmptech.intune.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;


@Document("Chats")
@Data
@AllArgsConstructor
public class ChatModel {
    @MongoId
    String Id;
    String roomId;
    String name;

    @DBRef
    List<UserModel> members;

    List<MessageModel> messages = new ArrayList<>();
}
