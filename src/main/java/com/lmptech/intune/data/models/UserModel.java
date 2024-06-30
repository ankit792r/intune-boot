package com.lmptech.intune.data.models;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Document("Users")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", verified=" + verified +
                '}';
    }
}
