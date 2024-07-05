package com.lmptech.intune.data.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;


@Document("Chats")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatModel {
    @MongoId
    String Id;
    String roomId;
    String name;

    List<String> members;

    List<MessageModel> messages;
}
