package com.lmptech.intune.controllers;

import com.lmptech.intune.data.models.UserModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @PostMapping("login")
    public ResponseEntity<?> userLogin(@RequestBody UserModel userModel) {
        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }
}
