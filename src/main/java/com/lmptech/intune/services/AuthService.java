package com.lmptech.intune.services;

import com.lmptech.intune.data.models.UserModel;
import com.lmptech.intune.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Map<String, Object> login(UserModel userModel) throws Exception {
        Query query = Query.query(
                Criteria.where("username").is(userModel.getUsername()));

        List<UserModel> userModels = mongoTemplate.find(query, UserModel.class, "Users");

        if (userModels.isEmpty()) throw new Exception("User not found");
        UserModel userModel1 = userModels.getFirst();

        if (userModel1.getPassword().equals(userModel.getPassword())) {
            userModel1.setPassword("");
            Map<String, Object> res = new HashMap<>();
            res.put("token", jwtUtility.generateJWTToken(new HashMap<>(), userModel1.getId()));
            res.put("user", userModel1);

            return res;
        } else throw new Exception("wrong password");
    }

    public String verify(String token) {
        return "";
    }
}
