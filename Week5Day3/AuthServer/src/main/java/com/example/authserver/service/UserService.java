package com.example.authserver.service;

//import com.example.authserver.dao.UserDao;
import com.example.authserver.UserServiceFeignClient;
import com.example.authserver.domain.request.SignupRequest;
//import com.example.authserver.entity.Permission;
//import com.example.authserver.entity.Permission;
import com.example.authserver.entity.User;
import com.example.authserver.exception.InvalidCredentialsException;
import com.example.authserver.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

//    private UserDao userDao;

//    @Autowired
//    public void setUserDao(UserDao userDao) {
//        this.userDao = userDao;
//    }
    @Autowired
    private UserServiceFeignClient userServiceFeignClient;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userServiceFeignClient.getUserByEmail(username);
//                userDao.loadUserByUsername(username);

//        System.out.println("user" + userOptional.get().getPermissions().size());

        if (!userOptional.isPresent()){
            throw new UsernameNotFoundException("Username does not exist");
        }

        User user = userOptional.get(); // database user

        return AuthUserDetail.builder() // spring security's userDetail
                .userid(user.getUserId())
                .username(user.getEmail())
//                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .password(user.getPassword())
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }

//    public void signup(SignupRequest request) {
//        userDao.signup(request);
//    }

    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();
//        for (Permission permission :  user.getPermissions()){
//            userAuthorities.add(new SimpleGrantedAuthority(permission.getPermission_name()));
////        }
        if (user.getType().equals("admin")){
            userAuthorities.add(new SimpleGrantedAuthority("admin"));
        }
        if (user.getType().equals("user")){
            userAuthorities.add(new SimpleGrantedAuthority("user"));
        }

        return userAuthorities;
    }
}
