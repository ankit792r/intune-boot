package com.lmptech.intune.services;

import com.lmptech.intune.data.models.UserModel;
import com.lmptech.intune.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Map<String, String> login(UserModel userModel) throws Exception {
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userModel.getUsername(), userModel.getPassword()));

        if (authenticate.isAuthenticated()) {
            User principal = (User) authenticate.getPrincipal();

            String access = jwtUtility.generateJWTToken(new HashMap<>(), principal.getUsername(), true);
            String refresh = jwtUtility.generateJWTToken(new HashMap<>(), principal.getUsername(), false);

            Map<String, String> response = new HashMap<>();
            response.put("access", access);
            response.put("refresh", refresh);

            return response;
        } else throw new Exception("wrong username or password");
    }

    public Map<String, String> refreshToken(String userId) {
        String access = jwtUtility.generateJWTToken(new HashMap<>(), userId, true);
        Map<String, String> response = new HashMap<>();
        response.put("access", access);
        return response;
    }
}
