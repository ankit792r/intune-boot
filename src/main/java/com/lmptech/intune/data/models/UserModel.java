package com.lmptech.intune.data.models;


import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;

@Document("Users")
@Data
public class UserModel {
    @MongoId
    String id;

    String name;

    @Indexed(unique = true, background = true)
    String email;

    @Indexed(unique = true, background = true)
    String username;

    String password;

    Boolean verified;

    @DBRef
    List<ChatModel> chats = new ArrayList<>();

    @DBRef
    List<RequestModel> requests = new ArrayList<>();

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", verified=" + verified +
                ", chats=" + chats +
                ", requests=" + requests +
                '}';
    }
}
