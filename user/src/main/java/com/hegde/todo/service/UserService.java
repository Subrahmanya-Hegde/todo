package com.hegde.todo.service;

import com.hegde.todo.domain.User;
import com.hegde.todo.dto.CredentialsDto;
import com.hegde.todo.dto.SignupDto;
import com.hegde.todo.dto.UserDto;
import com.hegde.todo.exception.AppException;
import com.hegde.todo.mappers.UserMapper;
import com.hegde.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto findByEmail(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        return userMapper.toUserDto(user);
    }

    public UserDto login(CredentialsDto credentialsDto){
        User user = userRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())){
            return userMapper.toUserDto(user);
        }
        throw new AppException("Invalid Password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignupDto signupDto){
        Optional<User> userOptional = userRepository.findByEmail(signupDto.getEmail());
        if(userOptional.isPresent()){
            throw new AppException("User with email already exists", HttpStatus.BAD_REQUEST);
        }
        User user = userMapper.signupToUser(signupDto);
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signupDto.getPassword())));
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }
}
