package com.example.whatsappwebclone.controller;

import com.example.whatsappwebclone.exception.UserException;
import com.example.whatsappwebclone.model.User;
import com.example.whatsappwebclone.request.UpdateUserRequest;
import com.example.whatsappwebclone.response.ApiResponse;
import com.example.whatsappwebclone.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) throws UserException {
        User user= userService.findUserByProfile(token);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("query") String query){
        List<User> users = userService.searchUser(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest req,
                                                         @RequestHeader("Authorization") String token
    ) throws UserException {
        User user = userService.findUserByProfile(token);
        userService.updateUser(user.getId(), req);
        ApiResponse responseToken = new ApiResponse("User Updated Successfully", true);
        return new ResponseEntity<>(responseToken, HttpStatus.ACCEPTED);
    }
}









