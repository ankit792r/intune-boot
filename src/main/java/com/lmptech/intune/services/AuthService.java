package com.lmptech.intune.services;

import com.lmptech.intune.data.models.UserModel;
import com.lmptech.intune.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Map<String, Object> login(UserModel userModel) throws Exception {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));

        if (authenticate.isAuthenticated()) {
            Query query = Query.query(
                    Criteria.where("username").is(userModel.getUsername()));

            UserModel dbUser = mongoTemplate.find(query, UserModel.class, "Users").getFirst();
            dbUser.setPassword(null);
            String token = jwtUtility.generateJWTToken(new HashMap<>(), dbUser.getId());

            Map<String, Object> res = new HashMap<>();
            res.put("token", token);
            res.put("user", dbUser);

            return res;
        } else throw new Exception("wrong username or password");
    }

    public String verify(String token) {
        return "";
    }
}
