package com.lmptech.intune.controllers;

import com.lmptech.intune.data.models.UserModel;
import com.lmptech.intune.data.models.response.ErrorMessage;
import com.lmptech.intune.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<?> userLogin(@RequestBody UserModel userModel) {
        try {
            Map<String, String> res = authService.login(userModel);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("refresh-token")
    public ResponseEntity<?> refreshToken() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            Map<String, String> res = authService.refreshToken(principal.getUsername());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
