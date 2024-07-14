package com.lmptech.intune.controllers;

import com.lmptech.intune.models.UserModel;
import com.lmptech.intune.models.ErrorMessage;
import com.lmptech.intune.services.UserService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    List<String> sections = Arrays.asList("profile", "account");

    @Autowired
    private UserService userService;

    @PostMapping("profile")
    public ResponseEntity<?> getUserProfile(@RequestBody List<String> ids) {
        List<UserModel> userProfile = userService.getUserProfile(ids);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @GetMapping("my-profile")
    public ResponseEntity<?> getUserData() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            UserModel userProfile = userService.getUserData(principal.getUsername());
            return new ResponseEntity<>(userProfile, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage("email or username already used"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("register")
    public ResponseEntity<?> addUser(@RequestBody UserModel userModel) {
        try {
            userService.createUser(userModel);
            Map<String, String> res = new HashMap<>();
            res.put("message", "created");
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage("email or username already used"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update/{section}")
    public ResponseEntity<?> updateProfile(@PathVariable String section, @RequestBody UserModel userModel) {
        if (!sections.contains(section))
            return new ResponseEntity<>(new ErrorMessage("update section is not defined"), HttpStatus.BAD_REQUEST);

        try {
            UpdateResult updateResult = userService.updateUser(userModel, section);
            if (updateResult.wasAcknowledged())
                return new ResponseEntity<>(updateResult, HttpStatus.OK);
            else
                return new ResponseEntity<>(new ErrorMessage("something went wrong"), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            return new ResponseEntity<>(new ErrorMessage(e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
