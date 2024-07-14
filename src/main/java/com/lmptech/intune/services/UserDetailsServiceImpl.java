package com.lmptech.intune.services;


import com.lmptech.intune.data.models.UserDetailsImpl;
import com.lmptech.intune.data.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));

        List<UserModel> userModels = mongoTemplate.find(query, UserModel.class);
        if (userModels.isEmpty()) throw new UsernameNotFoundException("Unknown user");
        return User.withUserDetails(new UserDetailsImpl(userModels.getFirst())).build();
    }

    public UserDetails loadUserById(String userId) throws UsernameNotFoundException {
        UserModel userModel = mongoTemplate.findById(userId, UserModel.class);
        if (userModel == null) throw new UsernameNotFoundException("Unknown user");
        userModel.setUsername(userId); // replacing username form userid because jwt filter needs to verify with id
        return User.withUserDetails(new UserDetailsImpl(userModel)).build();
    }
}
