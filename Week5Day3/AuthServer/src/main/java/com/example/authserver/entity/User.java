package com.example.authserver.entity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import java.sql.Timestamp;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import lombok.*;
//
//import javax.persistence.*;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//
//@Entity
//@Table(name = "User")
//@Getter
//@Setter
//@ToString
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
////@AllArgsConstructor
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "user_id", unique = true, nullable = false)
//    private Integer user_id;
//    @Column
//    @NotBlank(message = "username cannot be blank")
//    private String username;
//    @Column
//    @NotBlank(message = "password cannot be blank")
//    private String password;
//    @Column
//    @Email(message = "email should be valid")
//    @NotBlank(message = "email cannot be blank")
//    private String email;
//    @Column
//    private String role;
//
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @JoinTable(
//            name = "user_permission",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id"))
//    private Set<Permission> permissions = new HashSet<>();
//    public void addPermission(Permission permission) {
//        this.permissions.add(permission);
//        permission.getUsers().add(this);
//    }
//
//    public User(String username, String email, String password, String role) {
//        this.username = username;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }
////    private List<String> permissions;
//}
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", unique = false, nullable = false)
    private Integer userId;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    @Column(name = "firstName")
    private String firstName;

    @Column(nullable = false, name = "lastName")
    private String lastName;

    @Column(nullable = false, name = "active")
    private Boolean active;

    @Column(nullable = false, name = "dateJoined")
    private Timestamp dateJoined;

    @Column(nullable = false, name = "type")
    private String type;

    @Column(nullable = false, name = "profileImageURL")
    private String profileImageURL;
}
