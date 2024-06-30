package com.lmptech.intune.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.lmptech.intune.data.models.UserModel;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {


}
