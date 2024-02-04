package com.example.authserver;

import com.example.authserver.domain.request.LoginRequest;
import com.example.authserver.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;


@FeignClient(name = "user-service")
public interface UserServiceFeignClient {
    @PostMapping("/signup")
    User signup(@RequestBody User user);

    @PostMapping("/login")
    User login(@RequestBody LoginRequest loginRequest);
    @GetMapping("/user/{email}")
    Optional<User> getUserByEmail(@PathVariable("email") String email);

}
