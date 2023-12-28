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
        Optional<User> user =userRepository.findByEmail(email);

        if(user.isPresent()) {
            return user.get();
        }
        throw new BadCredentialsException("Received invalid token ");
    }

    @Override
    public User updateUser(Integer getId, UpdateUserRequest req) throws UserException {
        User user = findUserById(getId);
        if(req.getFull_name()!=null) {
            user.setFull_name(req.getFull_name());
        }
        if(req.getProfile_picture()!=null) {
            user.setProfile_picture(req.getProfile_picture());
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }
}
