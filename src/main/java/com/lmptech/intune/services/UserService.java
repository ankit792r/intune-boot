package com.lmptech.intune.services;

import com.lmptech.intune.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
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

    public List<UserModel> getUserProfile(String userId, List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").in(ids));

        if (ids.size() == 1 && ids.contains(userId))
            query.fields().exclude("chatIds", "requestIds", "password");
        else
            query.fields().include("_id", "username");

        return mongoTemplate.find(query, UserModel.class);
    }

    public void createUser(UserModel userModel) {
        userModel.setRequestIds(new ArrayList<>());
        userModel.setChatIds(new ArrayList<>());
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        mongoTemplate.insert(userModel, "Users");
    }
}
