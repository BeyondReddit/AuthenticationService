package com.example.authserver.controller;

import com.example.authserver.EmailServiceFeignClient;
import com.example.authserver.UserServiceFeignClient;
import com.example.authserver.domain.request.LoginRequest;
import com.example.authserver.domain.request.RegisterRequest;
import com.example.authserver.entity.User;
import com.example.authserver.entity.User;
import com.example.authserver.entity.UserInfo;
import com.example.authserver.security.AuthUserDetail;
import com.example.authserver.security.JwtProvider;
//import com.example.authserver.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @Autowired
    private EmailServiceFeignClient emailServiceFeignClient;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private JwtProvider jwtProvider;
    @Value("${security.jwt.token.key}")
    private String key;
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest user) {
        // Call User Service to signup
        System.out.println(user.getEmail());
        System.out.println(user.getFirstName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword());
        User unVerifiedUser = userServiceFeignClient.signup(user);

        // Send verification email
//        emailServiceFeignClient.sendEmail(unVerifiedUser);

        return ResponseEntity.ok("Signup successful. Check your email for verification.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Call User Service to login
        User user = userServiceFeignClient.login(loginRequest);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Authentication authentication;

        //Try to authenticate the user using the username and password
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
        } catch (AuthenticationException e){
            throw new BadCredentialsException("Provided credential is invalid.");
        }

        //Successfully authenticated user will be stored in the authUserDetail object
        AuthUserDetail authUserDetail = (AuthUserDetail) authentication.getPrincipal(); //getPrincipal() returns the user object

        //A token wil be created using the username/email/userId and permission
        String token = jwtProvider.createToken(authUserDetail);
        System.out.println("token" + token);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/getCurrentUser/{token}")
    public ResponseEntity<UserInfo> getCurrentUser(@RequestHeader("Authorization") String token) {
//    public ResponseEntity<UserInfo> getCurrentUser(@PathVariable String token) {
        // Validate and extract user from JWT token

//        Optional<AuthUserDetail> user = jwtProvider.resolveToken(token);

        if (token == null ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token1 = token.substring(7); // remove the prefix "Bearer "
        System.out.println(token);

        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token1).getBody(); // decode

        String username = claims.getSubject();
        System.out.println(username);
        List<LinkedHashMap<String, String>> permissions = (List<LinkedHashMap<String, String>>) claims.get("permissions");

        // convert the permission list to a list of GrantedAuthority
        List<GrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.get("authority")))
                .collect(Collectors.toList());

        //return a userDetail object with the permissions the user has
        UserInfo user = new UserInfo();
        user.setUserId(claims.get("userId", Integer.class));
        user.setAuthorities(authorities);
        System.out.println(user.getUserId());
        System.out.println(user.getAuthorities());
        return ResponseEntity.ok(user);
    }

}
