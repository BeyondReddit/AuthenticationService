package com.example.authserver.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
@Data
public class UserInfo {
    private int userId;
    private List<GrantedAuthority> authorities;
}
