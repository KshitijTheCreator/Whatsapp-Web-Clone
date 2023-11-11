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
    private ChatRepository chatRepository;
    private UserService userService;
    public ChatServiceImpl(ChatRepository chatRepository, UserService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }
    @Override
    public Chat createChat(User reqUser, Integer userId2) throws UserException {
        User user = userService.findUserById(userId2);
        Chat chatExist = chatRepository.findSingleChatByUserIds(user, reqUser);
        if(chatExist!= null) {
            return chatExist;
        }
        //if no chat present we have to create new chat
        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(user);
        chat.getUsers().add(reqUser);
        chat.setGroup(false);

        return chat;
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
    public Chat addUserToGroup(Integer userId, Integer chatId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt= chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if(opt.isPresent()) {
            Chat chat =opt.get();
            if(chat.getAdmins().contains(reqUser)) { // as only admin can add users
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            }
            else throw new UserException("Only admins can perform this task");
        }
        throw new ChatException("Chat doesnt exists with id: "+ chatId);
    }

    @Override
    public Chat createGroup(GroupChatRequest req, User admin) throws UserException {
        Chat group = new Chat();
        group.setGroup(true);
        group.setChat_image(req.getChat_image());
        group.setChat_name(req.getChat_name());
        group.setCreatedBy(admin);
        group.getAdmins().add(admin);

        for(Integer userId: req.getUserIds()) {
            User groupMembers =userService.findUserById(userId);
            group.getUsers().add(groupMembers);
        }
        return group;
    }

    @Override
    public Chat renameGroup(Integer chatId, String groupName, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if(opt.isPresent()) {
            Chat chat = opt.get();
            if(chat.getUsers().contains(reqUser)) {
                chat.setChat_name(groupName);
                chat.setChat_name(groupName);
                return chatRepository.save(chat);
            }
            throw new ChatException("You are not allowed to perform the task");
        }
        throw new ChatException("Chat doesnt exists with id: "+ chatId);
    }

    @Override
    public void deleteChat(Integer chatId, Integer userId) throws ChatException, UserException {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if(opt.isPresent()) {
            Chat chat = opt.get();
            chatRepository.deleteById(chat.getId());
        }
    }

    @Override
    public Chat removeFromGroup(Integer chatId, Integer userId, User reqUser) throws UserException, ChatException {
        Optional<Chat> opt= chatRepository.findById(chatId);
        User user = userService.findUserById(userId);
        if(opt.isPresent()) {
            Chat chat =opt.get();
            if(chat.getAdmins().contains(reqUser)) { // as only admin can add users
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            }
            else if(chat.getUsers().contains(reqUser)) { // the requested user can remove or leave the group
                if(user.getId().equals(reqUser.getId())) {
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }
            }
            throw new UserException("Only admins can remove other members.");
        }
        throw new ChatException("Chat doesnt exists with id: "+ chatId);
    }
}
