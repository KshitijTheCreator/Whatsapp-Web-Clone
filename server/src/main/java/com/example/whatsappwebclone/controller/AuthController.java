package com.example.whatsappwebclone.controller;

import com.example.whatsappwebclone.config.TokenProvider;
import com.example.whatsappwebclone.exception.UserException;
import com.example.whatsappwebclone.model.User;
import com.example.whatsappwebclone.repository.UserRepository;
import com.example.whatsappwebclone.request.LoginRequest;
import com.example.whatsappwebclone.response.AuthResponse;
import com.example.whatsappwebclone.service.CustomUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final CustomUserService customUserService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider, CustomUserService customUserService)  {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.customUserService = customUserService;
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException {
        String email = user.getEmail();
        String name = user.getFull_name();
        String password = user.getPassword();

        Optional<User> isUser = userRepository.findByEmail(email);
        if (isUser.isPresent()) {
            throw new UserException("Email Seems to be already registered");
        }
        //if user is not registered we can create new user for that for signup
        User createNewUser = new User();
        createNewUser.setEmail(email);
        createNewUser.setFull_name(name);
        createNewUser.setPassword(passwordEncoder.encode(password));


        userRepository.save(createNewUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(jwt, true);

        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginHeader(@RequestBody LoginRequest req) {
        String email = req.getEmail();
        String password = req.getPassword();
        Authentication authentication = authenticate(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(jwt, true);
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }
    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if(userDetails == null) throw new BadCredentialsException("invalid username");
        if(!passwordEncoder.matches(password, userDetails.getPassword())) throw new BadCredentialsException("invalid password or username");
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
