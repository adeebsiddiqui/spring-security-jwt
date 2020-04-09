package com.ad.app.model;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
