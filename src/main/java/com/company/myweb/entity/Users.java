package com.company.myweb.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity(name = "users")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "creat_date")
    private Date createDate;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles roles;
}
