package com.lmptech.intune.controllers;

import com.lmptech.intune.models.ChatModel;
import com.lmptech.intune.models.ErrorMessage;
import com.lmptech.intune.models.RequestModel;
import com.lmptech.intune.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("user-chats")
    public ResponseEntity<?> getUserChats(){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            List<ChatModel> userChats = chatService.getUserChats(principal.getUsername());
            return new ResponseEntity<>(userChats, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user-requests")
    public ResponseEntity<?> userRequests() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            List<RequestModel> requestModels = chatService.getUserRequests(principal.getUsername());
            return new ResponseEntity<>(requestModels, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("byId/{chatId}")
    public ResponseEntity<?> getChatData(@PathVariable("chatId") String chatId) {
        try {
            ChatModel chatData = chatService.getChatData(chatId);
            return new ResponseEntity<>(chatData, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    // all methods below will be call with socket events
    @PostMapping("new-request")
    public ResponseEntity<?> newRequest(@RequestBody Map<String, String> requestModel) {
        try {
            RequestModel result = chatService.newChatRequest(requestModel);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("cancel-request/{requestId}")
    public ResponseEntity<?> cancelRequest(@PathVariable String requestId) {
        try {
            RequestModel result = chatService.cancelChatRequest(requestId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("accept-request/{requestId}")
    public ResponseEntity<?> acceptRequest(@PathVariable String requestId) {
        try {
            ChatModel result = chatService.acceptChatRequest(requestId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorMessage(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
