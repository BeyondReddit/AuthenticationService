package com.example.authserver;

import com.example.authserver.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "email-service")
public interface EmailServiceFeignClient {
    @PostMapping("/sendEmail")
    void sendEmail(@RequestBody User user);
}
