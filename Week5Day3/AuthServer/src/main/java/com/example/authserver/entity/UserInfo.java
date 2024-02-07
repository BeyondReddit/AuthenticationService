package com.example.authserver.entity;

import lombok.Data;

@Data
public class UserInfo {
    private int userId;
    private String authorities;
}
