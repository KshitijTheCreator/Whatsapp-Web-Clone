package com.example.whatsappwebclone.controller.mapper;

import com.example.whatsappwebclone.DTO.UserDTO;
import com.example.whatsappwebclone.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserDTOMapper {
    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFull_name(user.getFull_name());
        userDTO.setProfile_picture(user.getProfile_picture());

        return userDTO;
    }
    public static HashSet<UserDTO> toUserDTOs(Set<User> users) {
        HashSet<UserDTO> userDTOs = new HashSet<>();
        for(User user: users) {
            UserDTO userDto = toUserDTO(user);
            userDTOs.add(userDto);
        }
        return userDTOs;
    }
}
