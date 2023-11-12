package com.hegde.todo.controller;

import com.hegde.todo.dto.CredentialsDto;
import com.hegde.todo.dto.SignupDto;
import com.hegde.todo.dto.UserDto;
import com.hegde.todo.service.JwtService;
import com.hegde.todo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("v0/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * This is the login controller. Provide your user email and password for jwt generation.
     * @param credentialsDto
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto) {
        UserDto userDto = userService.login(credentialsDto);
        userDto.setToken(jwtService.createToken(userDto.getEmail()));
        return ResponseEntity.ok(userDto);
    }

    /**
     * This is the register controller. Use this controller for user registration and generating the jwt token.
     * @param signupDto
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignupDto signupDto) {
        UserDto userDto = userService.register(signupDto);
        userDto.setToken(jwtService.createToken(userDto.getEmail()));
        return ResponseEntity
                .created(URI.create("/users/" + userDto.getId()))
                .body(userDto);
    }
}
