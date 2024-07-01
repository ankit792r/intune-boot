package com.lmptech.intune.controllers;

import com.lmptech.intune.data.models.UserModel;
import com.lmptech.intune.data.models.response.ErrorMessage;
import com.lmptech.intune.services.UserService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<UserModel> allUser() {
        return userService.getAllUser();
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable String id) {
        List<UserModel> userProfile = userService.getUserProfile(id);
        if (userProfile.isEmpty()) return new ResponseEntity<>(new ErrorMessage("user not found"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userProfile.getFirst(), HttpStatus.OK);
    }

    @PostMapping("new")
    public ResponseEntity<?> addUser(@RequestBody UserModel userModel) {
        try {
            userService.createUser(userModel);
            return new ResponseEntity<>("created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage("email or username already used"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> updateUser(@RequestBody UserModel userModel) {
        UpdateResult updateResult = userService.updateUser(userModel);
        if (updateResult.wasAcknowledged())
            return new ResponseEntity<>(updateResult, HttpStatus.OK);
        else
            return new ResponseEntity<>(new ErrorMessage("something went wrong"), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
