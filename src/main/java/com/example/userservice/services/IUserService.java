package com.example.userservice.services;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;

public interface IUserService {
    public User signup(String email,String password, String name);
    public Token login(String email,String password);

    public void logout(String token);

    public User validateToken(String token);
}
