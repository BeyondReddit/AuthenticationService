package com.example.authserver;

import com.example.authserver.domain.request.LoginRequest;
import com.example.authserver.domain.request.RegisterRequest;
import com.example.authserver.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@FeignClient(name = "userservice")
public interface UserServiceFeignClient {
    @PostMapping("/users/newUser")
    User signup(@RequestBody RegisterRequest registerRequest);

    @PostMapping("/users/getUser")
    User login(@RequestBody LoginRequest loginRequest);
    @GetMapping("/users/getUserByEmail/{email}")
    Optional<User> getUserByEmail(@PathVariable("email") String email);

}
