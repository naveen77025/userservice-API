package com.example.userservice.controllers;

import com.example.userservice.dto.LogOutRequestDto;
import com.example.userservice.dto.LoginRequestDto;
import com.example.userservice.dto.SignUpRequestDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto){
        return iUserService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }
    @PostMapping("/signup")
    public User signUp(@RequestBody SignUpRequestDto signUpRequestDto){
        String email= signUpRequestDto.getEmail();
        String password= signUpRequestDto.getPassword();
        String name= signUpRequestDto.getName();
        return iUserService.signup(email,password,name);
    }
    @PostMapping("/logout") public ResponseEntity<Void> logOut(@RequestBody LogOutRequestDto logOutRequestDto){
        iUserService.logout(logOutRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validatetoken/{token}")
    public User validateToken(@PathVariable("token") String token){
       return iUserService.validateToken(token);
    }
}
