package com.lmptech.intune.services;

import com.lmptech.intune.data.models.ChatModel;
import com.lmptech.intune.data.models.RequestModel;
import com.lmptech.intune.data.models.UserModel;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ChatModel> getAllChats() {
        return mongoTemplate.findAll(ChatModel.class);
    }

    public List<RequestModel> getAllRequests() {
        return mongoTemplate.findAll(RequestModel.class);
    }

    public Map<String, Object> getUserChats(String userId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userId));
        query.fields().include("chats");

        UserModel userModel = mongoTemplate.findOne(query, UserModel.class);
        if (userModel == null) throw new Exception("user not found");

        Map<String, Object> res = new HashMap<>();
        res.put("chats", userModel.chats);

        return res;
    }

    // TODO make transaction
    public RequestModel newChatRequest(String senderUsername, String receiverUsername) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").in(senderUsername, receiverUsername));
        query.fields().include("_id", "requests");

        List<UserModel> userModels = mongoTemplate.find(query, UserModel.class);
        if (userModels.isEmpty()) throw new Exception(String.format("user with username %s is not exists", receiverUsername));

        RequestModel requests = mongoTemplate.insert(
                new RequestModel(null, userModels.getLast().getId(), userModels.getFirst().getId()), "Requests");

        Update update = new Update();
        update.push("requests", requests);
        mongoTemplate.updateMulti(query, update, UserModel.class);
        return requests;
    }

    // TODO make it transaction
    // the same method will be used for cancel and reject request
    public RequestModel cancelChatRequest(String requestId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(requestId));
        RequestModel removedRequest = mongoTemplate.findAndRemove(query, RequestModel.class);

        if (removedRequest == null) throw new Exception("request not exists");

        Query userQuery = new Query();
        userQuery.addCriteria(Criteria.where("_id").in(removedRequest.getSenderId(), removedRequest.getReceiverId()));
        userQuery.fields().include("_id", "requests");

        List<UserModel> userModels = mongoTemplate.find(userQuery, UserModel.class);
        if (userModels.isEmpty()) throw new Exception("users not found");

        Update update = new Update();
        update.pull("requests", removedRequest);
        mongoTemplate.updateMulti(userQuery, update, UserModel.class);

        return removedRequest;
    }

    public Map<String, Object> acceptChatRequest(String requestId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(requestId));
        RequestModel removedRequest = mongoTemplate.findAndRemove(query, RequestModel.class);

        if (removedRequest == null) throw new Exception("request not exists");


        Query userQuery = new Query();
        userQuery.addCriteria(Criteria.where("_id").in(removedRequest.getSenderId(), removedRequest.getReceiverId()));

        List<UserModel> userModels = mongoTemplate.find(userQuery, UserModel.class);
        if (userModels.isEmpty()) throw new Exception("users not found");

        ChatModel chatModel = mongoTemplate.insert(
                new ChatModel(null, "tst", "nam",
                        List.of(removedRequest.getSenderId(), removedRequest.getReceiverId()),
                        new ArrayList<>()), "Chats");

        Update update = new Update();
        update.pull("requests", removedRequest);
        update.push("chats", chatModel);
        mongoTemplate.updateMulti(userQuery, update, UserModel.class);

        Map<String, Object> result = new HashMap<>();
        result.put("request", removedRequest);
        result.put("chat", chatModel);
        return result;
    }
}
