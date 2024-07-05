package com.lmptech.intune.data.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("Requests")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RequestModel {
    @MongoId
    String id;
    String senderId;
    String receiverId;
}
