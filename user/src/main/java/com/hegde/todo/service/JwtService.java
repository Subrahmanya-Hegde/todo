package com.hegde.todo.service;

public interface JwtService {
    public String extractUserName(String jwtToken);
}
