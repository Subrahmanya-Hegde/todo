package com.hegde.todo.service;

import com.hegde.todo.domain.User;
import com.hegde.todo.domain.UserPrincipal;
import com.hegde.todo.exception.AppException;
import com.hegde.todo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
        return UserPrincipal.toUserPrincipal(user);
    }
}
