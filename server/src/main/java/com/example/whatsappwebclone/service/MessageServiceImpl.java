package com.example.whatsappwebclone.service;


import com.example.whatsappwebclone.exception.ChatException;
import com.example.whatsappwebclone.exception.MessageException;
import com.example.whatsappwebclone.exception.UserException;
import com.example.whatsappwebclone.model.Chat;
import com.example.whatsappwebclone.model.Message;
import com.example.whatsappwebclone.model.User;
import com.example.whatsappwebclone.repository.MessageRepository;
import com.example.whatsappwebclone.request.SendMessageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private UserService userService;
    private ChatService chatService;

    public MessageServiceImpl(MessageRepository messageRepository, UserService userService, ChatService chatService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    @Override
    public Message sendMessage(SendMessageRequest sendMessageRequest) throws ChatException, UserException {
        User user = userService.findUserById(sendMessageRequest.getUserId());
        Chat chat = chatService.findChatById(sendMessageRequest.getChatId());

        Message message = new Message();
        message.setChat(chat);
        message.setUser(user);
        message.setContent(sendMessageRequest.getContent());
        message.setTimeStamp(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getChatsMessages(Integer chatId, User reqUser) throws ChatException, UserException {
        Chat chat = chatService.findChatById(chatId);
        if(!chat.getUsers().contains(reqUser)) {
            throw new UserException("Access denied for chats");
        }
        List<Message> messages = messageRepository.findByChatId(chat.getId());
        return messages;
    }

    @Override
    public Message findMessageById(Integer messageId) throws MessageException {
        Optional<Message> opt = messageRepository.findById(messageId);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw new MessageException("Cannot locate message");
    }

    @Override
    public void deleteMessage(Integer messageId, User reqUser) throws MessageException, UserException {// only person who wrote can delete the message
        Message message = findMessageById(messageId);
        if(message.getUser().getId().equals(reqUser.getId())) {
            messageRepository.deleteById(messageId);
        }
        throw new UserException("Deletion not allowed");


    }
}
