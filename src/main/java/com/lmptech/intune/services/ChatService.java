package com.lmptech.intune.services;

import com.lmptech.intune.models.ChatModel;
import com.lmptech.intune.models.RequestModel;
import com.lmptech.intune.models.UserModel;
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

    public List<ChatModel> getUserChats(String userId) throws Exception {

        UserModel byId = mongoTemplate.findById(userId, UserModel.class);
        if (byId == null) throw new Exception("User not found");

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(byId.getChatIds()));

        return mongoTemplate.find(query, ChatModel.class);
    }

    // TODO make transaction
    public RequestModel newChatRequest(String senderUsername, String receiverUsername) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").in(senderUsername, receiverUsername));
        query.fields().include("name", "username");

        List<UserModel> userModels = mongoTemplate.find(query, UserModel.class);
        if (userModels.size() != 2) throw new Exception(String.format("user with username %s is not exists", receiverUsername));

        // TODO check weather sender is last or receiver
        RequestModel request = mongoTemplate.insert(
                new RequestModel(null, userModels.getFirst(), userModels.getLast()), "Requests");

        Update update = new Update();
        update.push("requestIds", request.getId());
        mongoTemplate.updateMulti(query, update, UserModel.class);
        return request;
    }

    // TODO make it transaction
    // the same method will be used for cancel and reject request
    public RequestModel cancelChatRequest(String requestId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(requestId));
        RequestModel removedRequest = mongoTemplate.findAndRemove(query, RequestModel.class);

        if (removedRequest == null) throw new Exception("request not exists");

        Query userQuery = new Query();
        userQuery.addCriteria(Criteria.where("_id").in(removedRequest.getSender().getId(), removedRequest.getReceiver().getId()));
        userQuery.fields().include("_id", "requests");

        List<UserModel> userModels = mongoTemplate.find(userQuery, UserModel.class);
        if (userModels.size() != 2) throw new Exception("users not found");

        Update update = new Update();
        update.pull("requestIds", removedRequest.getId());
        mongoTemplate.updateMulti(userQuery, update, UserModel.class);

        return removedRequest;
    }

    // TODO only receiver can accept the request
    public Map<String, Object> acceptChatRequest(String requestId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(requestId));
        RequestModel removedRequest = mongoTemplate.findAndRemove(query, RequestModel.class);

        if (removedRequest == null) throw new Exception("request not exists");

        Query userQuery = new Query();
        userQuery.addCriteria(Criteria.where("_id").in(removedRequest.getSender().getId(), removedRequest.getReceiver().getId()));

        ChatModel chatModel = mongoTemplate.insert(new ChatModel(null, "tst", "nam", List.of(removedRequest.getSender(), removedRequest.getReceiver()), new ArrayList<>()), "Chats");

        Update update = new Update();
        update.pull("requestIds", removedRequest.getId());
        update.push("chatIds", chatModel.getId());
        mongoTemplate.updateMulti(userQuery, update, UserModel.class);

        Map<String, Object> result = new HashMap<>();
        result.put("requestId", removedRequest.getId());
        result.put("chat", chatModel);
        return result;
    }

    public List<RequestModel> userRequests(String userId) throws Exception {
        UserModel byId = mongoTemplate.findById(userId, UserModel.class);
        if (byId == null) throw new Exception("user not found");
        List<String> requestIds = byId.getRequestIds();

        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(byId.getRequestIds()));

        return mongoTemplate.find(query, RequestModel.class);
    }
}
