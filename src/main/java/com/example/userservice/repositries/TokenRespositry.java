package com.example.userservice.repositries;

import com.example.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRespositry extends JpaRepository<Token,Long> {
    Token save(Token token);
    Optional<Token> findByValueAndIsDeleted(String Token,boolean isDeleted);
}
