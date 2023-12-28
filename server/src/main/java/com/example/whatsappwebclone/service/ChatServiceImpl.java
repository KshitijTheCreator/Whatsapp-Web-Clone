package com.example.whatsappwebclone.service;

import com.example.whatsappwebclone.exception.ChatException;
import com.example.whatsappwebclone.exception.UserException;
import com.example.whatsappwebclone.model.Chat;
import com.example.whatsappwebclone.model.User;
import com.example.whatsappwebclone.repository.ChatRepository;
import com.example.whatsappwebclone.request.GroupChatRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService{
    private final ChatRepository chatRepository;
    private final UserService userService;
    public ChatServiceImpl(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }
    @Override
    public Chat createChat(Integer reqUserId, Integer userId2) throws UserException {
        User reqUser = userService.findUserById(reqUserId);
        User user = userService.findUserById(userId2);

        Chat chatExist = chatRepository.findSingleChatByUserIds(user, reqUser);
        if(chatExist!= null) {
            return chatExist;
        }
        //if no chat present we have to create new chat
        Chat chat = new Chat();
        chat.setCreated_by(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setIs_group(false);
        return chatRepository.save(chat);

//        return chat;
    }

    @Override
    public Chat findChatById(Integer userId) throws ChatException {
        Optional<Chat> chat = chatRepository.findById(userId);
        if(chat.isPresent()) {
            return chat.get();
        }
        throw new ChatException("Chat cannot be found with the given id: "+ userId);
    }

    @Override
    public List<Chat> findAllChatByUserId(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        return chatRepository.findChatByUserId(user.getId());

    }

    @Override
    public Chat addUserToGroup(Integer userId, Integer chatId) throws UserException, ChatException {
        Chat chat =findChatById(chatId);
        User user=userService.findUserById(userId);

        chat.getUsers().add(user);


        Chat updatedChat=chatRepository.save(chat);

        return updatedChat;
    }

    @Override
    public Chat createGroup(GroupChatRequest req, Integer adminId) throws UserException {
        Chat group = new Chat();
        User admin = userService.findUserById(adminId);
        group.setCreated_by(admin);
        group.getUsers().add(admin);

        for(Integer userId: req.getUserIds()) {
            User groupMembers =userService.findUserById(userId);
            group.getUsers().add(groupMembers);
        }

        group.setChat_name(req.getChat_name());
        group.setChat_image(req.getChat_image());
        group.setIs_group(true);
        group.getAdmins().add(admin);

        return chatRepository.save(group);
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, Integer reqUserId) throws UserException, ChatException {
        Chat chat=findChatById(chatId);
        User user=userService.findUserById(reqUserId);


        if(chat.getUsers().contains(user))
            chat.setChat_name(groupName);

        return chatRepository.save(chat);
    }

    @Override
    public Chat deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        User user=userService.findUserById(userId);
        Chat chat=findChatById(chatId);

        if((chat.getCreated_by().getId().equals(user.getId())) && !chat.getIs_group() ) {
            chatRepository.deleteById(chat.getId());

            return chat;
        }
        throw new ChatException("you dont have access to delete this chat");
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, Integer reqUserId) throws UserException, ChatException {
        Chat chat=findChatById(chatId);
        User user=userService.findUserById(userId);

        User reqUser=userService.findUserById(reqUserId);

        if(user.getId().equals(reqUser.getId()) ) {
            chat.getUsers().remove(reqUser);
        }

        return null;
    }
}
