package com.example.whatsappwebclone.controller;

import com.example.whatsappwebclone.DTO.ChatDTO;
import com.example.whatsappwebclone.controller.mapper.ChatDTOMapper;
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
    private final ChatService chatService;
    private final UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/single")
    public ResponseEntity<ChatDTO> createChatHandler(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chat = chatService.createChat(reqUser.getId(), singleChatRequest.getUserId());
        ChatDTO chatDTO= ChatDTOMapper.toChatDTO(chat);
        return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }
    @PostMapping("/group")
    public ResponseEntity<ChatDTO> createGroupHandler(@RequestBody GroupChatRequest req, @RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chat = chatService.createGroup(req, reqUser.getId());
        ChatDTO chatDTO = ChatDTOMapper.toChatDTO(chat);
        return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }
    @GetMapping("/{chatId}")
    public ResponseEntity<ChatDTO> findChatByIdHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws ChatException {
        Chat chat = chatService.findChatById(chatId);
        ChatDTO chatDTO  =ChatDTOMapper.toChatDTO(chat);
        return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }
    @GetMapping("/user")
    public ResponseEntity<List<ChatDTO>> findAllChatByUserIdHandler(@RequestHeader("Authorization")String jwt) throws UserException {
        User reqUser = userService.findUserByProfile(jwt);
        List<Chat> chats = chatService.findAllChatByUserId(reqUser.getId());
        List<ChatDTO> chatDTOs= ChatDTOMapper.toChatDTOs(chats);
            return new ResponseEntity<>(chatDTOs, HttpStatus.ACCEPTED);
    }
    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<ChatDTO> addUserToGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId) throws UserException, ChatException {
        Chat chats = chatService.addUserToGroup(userId, chatId);
        ChatDTO chatDTO = ChatDTOMapper.toChatDTO(chats);
        return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }
    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<ChatDTO> removeUserToGroupHandler(@PathVariable Integer chatId, @PathVariable Integer userId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserByProfile(jwt);
        Chat chat = chatService.removeFromGroup(userId, chatId, reqUser.getId());
        ChatDTO chatDTO = ChatDTOMapper.toChatDTO(chat);
        return new ResponseEntity<>(chatDTO, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChatHandler(@PathVariable Integer chatId, @RequestHeader("Authorization")String jwt) throws UserException, ChatException {
        User reqUser = userService.findUserByProfile(jwt);
        chatService.deleteChat(chatId, reqUser.getId());
        ApiResponse res = new ApiResponse("chat deleted successfully", false);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
