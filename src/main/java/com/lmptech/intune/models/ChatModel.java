package com.lmptech.intune.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;


@Document("Chats")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatModel {
    @MongoId
    String id;
    String roomId;
    String name;
    List<UserModel> members;
    List<MessageModel> messages;
}
