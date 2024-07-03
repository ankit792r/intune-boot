package com.lmptech.intune.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("Requests")
@AllArgsConstructor
@NoArgsConstructor
public class RequestModel {
    @MongoId
    String id;
    String senderId;
    String receiverId;
}
