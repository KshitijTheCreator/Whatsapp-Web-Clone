package com.example.whatsappwebclone.controller;

import com.example.whatsappwebclone.DTO.UserDTO;
import com.example.whatsappwebclone.controller.mapper.UserDTOMapper;
import com.example.whatsappwebclone.exception.UserException;
import com.example.whatsappwebclone.model.User;
import com.example.whatsappwebclone.request.UpdateUserRequest;
import com.example.whatsappwebclone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("/api/users")
public class UserController{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user= userService.findUserByProfile(token);
        UserDTO userDTO = UserDTOMapper.toUserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<HashSet<UserDTO>> searchUserHandler(@RequestParam("query") String query){
        HashSet<User> users = new HashSet<>(userService.searchUser(query));
        HashSet<UserDTO> userDTOs= UserDTOMapper.toUserDTOs(users);
        return new ResponseEntity<>(userDTOs, HttpStatus.OK);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<UserDTO> updateUserHandler(
            @PathVariable Integer userId,
            @RequestBody UpdateUserRequest req
    ) throws UserException {

        User updatedUser=userService.updateUser(userId, req);
        UserDTO userDTO = UserDTOMapper.toUserDTO(updatedUser);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}









