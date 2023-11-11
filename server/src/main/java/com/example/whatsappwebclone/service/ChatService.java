package com.example.whatsappwebclone.service;

import com.example.whatsappwebclone.exception.ChatException;
import com.example.whatsappwebclone.exception.UserException;
import com.example.whatsappwebclone.model.Chat;
import com.example.whatsappwebclone.model.User;
import com.example.whatsappwebclone.request.GroupChatRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ChatService {
    public Chat createChat(User reqUser, Integer userId) throws UserException;

    public Chat findChatById(Integer userId) throws ChatException;

    public List<Chat> findAllChatByUserId(Integer userId) throws UserException;

    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException;
    public Chat createGroup(GroupChatRequest req, User user) throws UserException;
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws UserException, ChatException;
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException;
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException;
}
