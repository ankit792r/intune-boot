package com.lmptech.intune.controllers;

import com.lmptech.intune.data.models.RequestModel;
import com.lmptech.intune.data.models.response.ErrorMessage;
import com.lmptech.intune.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("all")
    public ResponseEntity<?> getAllChats() {
        return new ResponseEntity<>(chatService.getAllChats(), HttpStatus.OK);
    }

    @GetMapping("all-requests")
    public ResponseEntity<?> getAllRequests() {
        return new ResponseEntity<>(chatService.getAllRequests(), HttpStatus.OK);
    }

    @GetMapping("new")
    public ResponseEntity<?> newRequest() {
        try {

            RequestModel result = chatService.newChatRequest("ankit", "indra");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("cancel/{requestId}")
    public ResponseEntity<?> cancelRequest(@PathVariable String requestId) {
        try {
            RequestModel result = chatService.cancelChatRequest(requestId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("accept/{requestId}")
    public ResponseEntity<?> test(@PathVariable String requestId) {
        try {
            Map<String, Object> result = chatService.acceptChatRequest(requestId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
