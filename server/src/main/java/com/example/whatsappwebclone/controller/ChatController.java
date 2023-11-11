package com.example.whatsappwebclone.controller;

import com.example.whatsappwebclone.exception.ChatException;
import com.example.whatsappwebclone.exception.UserException;
import com.example.whatsappwebclone.model.Chat;
import com.example.whatsappwebclone.model.User;
import com.example.whatsappwebclone.request.GroupChatRequest;
import com.example.whatsappwebclone.request.SingleChatRequest;
import com.example.whatsappwebclone.response.ApiResponse;
import com.example.whatsappwebclone.service.ChatService;
import com.example.whatsappwebclone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private ChatService chatService;
    private UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/single")
    public ResponseEntity<Chat> createChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chat = chatService.createChat(reqUser, singleChatRequest.getUserId());
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
    @PostMapping("/group")
    public ResponseEntity<Chat> createGroupHandler(@RequestBody GroupChatRequest req, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chat = chatService.createGroup(req, reqUser);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByIdHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws ChatException {
        Chat chat = chatService.findChatById(chatId);
        return new ResponseEntity<>(chat, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChatByUserIdHandler(@RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserByProfile(jwt);
        List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }
    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> addUserToGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chats = chatService.addUserToGroup(userId, chatId, reqUser);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }
    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserToGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chats = chatService.removeFromGroup(userId, chatId, reqUser);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserByProfile(jwt);
        chatService.deleteChat(chatId, reqUser.getId());
        ApiResponse res = new ApiResponse("chat deleted successfully", false);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }



}
