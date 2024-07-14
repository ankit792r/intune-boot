package com.lmptech.intune.services;

import com.lmptech.intune.models.UserModel;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<UserModel> getUserProfile(List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(ids));
        query.fields().include("_id", "username");
        return mongoTemplate.find(query, UserModel.class);
    }

    public UserModel getUserData(String userId) throws Exception {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(userId));
        query.fields().exclude("chats", "password");

        UserModel userModel = mongoTemplate.findOne(query, UserModel.class);
        if (userModel == null) throw new Exception("user not found");
        return userModel;
    }

    public void createUser(UserModel userModel) {
        userModel.setRequestIds(new ArrayList<>());
        userModel.setChatIds(new ArrayList<>());
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        mongoTemplate.insert(userModel, "Users");
    }
}
