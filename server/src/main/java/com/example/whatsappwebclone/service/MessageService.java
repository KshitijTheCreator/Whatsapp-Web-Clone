package com.example.whatsappwebclone.service;

import com.example.whatsappwebclone.exception.ChatException;
import com.example.whatsappwebclone.exception.MessageException;
import com.example.whatsappwebclone.exception.UserException;
import com.example.whatsappwebclone.model.Message;
import com.example.whatsappwebclone.model.User;
import com.example.whatsappwebclone.request.SendMessageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {
    public Message sendMessage(SendMessageRequest sendMessageRequest) throws ChatException, UserException;
    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException;
    public Message findMessageById(Integer messageId) throws MessageException;

    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException;
}
