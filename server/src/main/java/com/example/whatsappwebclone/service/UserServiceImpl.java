package com.example.whatsappwebclone.service;

import com.example.whatsappwebclone.config.TokenProvider;
import com.example.whatsappwebclone.exception.UserException;
import com.example.whatsappwebclone.model.User;
import com.example.whatsappwebclone.repository.UserRepository;
import com.example.whatsappwebclone.request.UpdateUserRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserServiceImpl(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }
    @Override
    public User findUserById(Integer id) throws  UserException {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("User Not Found");
    }

    @Override
    public User findUserByProfile(String jwt) throws UserException{
        String email = tokenProvider.getEmailFromToken(jwt);
        if(email.isEmpty()) {
            throw new BadCredentialsException("Received invalid token ");
        }
        User user =userRepository.findByEmail(email);

        if(user == null) {
            throw new UserException("User Not Registered with the email provided");
        }
        return user;
    }

    @Override
    public User updateUser(Integer getId, UpdateUserRequest req) throws UserException {
        User user = findUserById(getId);
        if(user.getFull_name() != null) user.setFull_name(req.getName());
        if(user.getProfilePicture() != null) user.setProfilePicture(req.getProfilePicture());
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
