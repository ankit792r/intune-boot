package com.lmptech.intune.data.models;


import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.util.List;

@Document("Users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserModel {
    @MongoId
    public String id;

    public String name;

    @Indexed(unique = true, background = true)
    public String email;

    @Indexed(unique = true, background = true)
    public String username;

    public String password;

    @DBRef
    public List<ChatModel> chats;

    @DBRef
    public List<RequestModel> requests;

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", chats=" + chats +
                ", requests=" + requests +
                '}';
    }
}
