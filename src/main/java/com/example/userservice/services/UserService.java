package com.example.userservice.services;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositries.TokenRespositry;
import com.example.userservice.repositries.UserRespositry;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperatorExtensionsKt;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private UserRespositry userRespositry;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRespositry tokenRespositry;

    public UserService(UserRespositry userRespositry, BCryptPasswordEncoder bCryptPasswordEncoder,TokenRespositry tokenRespositry) {
        this.userRespositry = userRespositry;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRespositry=tokenRespositry;
    }

    @Override
    public User signup(String email, String password, String name) {
        User user= new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        return userRespositry.save(user);
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> userOptional = userRespositry.findByEmail(email);
        if(userOptional.isEmpty()){
            return null;
        }
        User user= userOptional.get();
        if(!bCryptPasswordEncoder.matches(password,user.getHashedPassword())){
            return null;
        }
        LocalDate today= LocalDate.now();
        LocalDate thirtyDaysLater= today.plusDays(30);
        Date expirydate= Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setUser(user);
        token.setExpiry(expirydate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        return tokenRespositry.save(token);
    }

    @Override
    public void logout(String token) {
        Optional<Token> Optionaltoken= tokenRespositry.findByValueAndIsDeleted(token,false);
        if(token.isEmpty()){

        }
        Token token1= Optionaltoken.get();
        token1.setDeleted(true);
        tokenRespositry.save(token1);

    }

    @Override
    public User validateToken(String token) {
        Optional<Token> tkn = tokenRespositry.findByValueAndIsDeleted(token,false);
        if(tkn.isEmpty()){
            return null;
        }
        return tkn.get().getUser();
    }
}
