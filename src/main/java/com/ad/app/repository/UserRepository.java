package com.ad.app.repository;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private final PasswordEncoder passwordEncoder;
    private final List<UserDetails> userList ;

    public UserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userList = retrieveListOfUsers();
    }

    public Optional<UserDetails> getUserDetailsByUsername(String username) {
        return userList.stream()
                .filter(u -> username.equals(u.getUsername()))
                .findFirst();
    }

    private List<UserDetails> retrieveListOfUsers() {
        return List.of(
                User.builder()
                        .username("johnwick")
                        .password(passwordEncoder.encode("Excommunicado"))
                        .roles("ASSASIN")
                        .build()
        );
    }
}