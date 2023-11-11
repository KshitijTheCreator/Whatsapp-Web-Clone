package com.example.whatsappwebclone.service;

import com.example.whatsappwebclone.exception.UserException;
import com.example.whatsappwebclone.model.User;
import com.example.whatsappwebclone.request.UpdateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User findUserById(Integer id) throws UserException;
    public User findUserByProfile(String jwt) throws UserException;
    public User updateUser(Integer userId, UpdateUserRequest req) throws UserException;
    public List<User> searchUser(String query);
}
