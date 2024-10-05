package com.company.myweb.dto;

import com.company.myweb.entity.Role;
import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private int id;
    private String userName;
    private String password;
    private String fullName;
    private Date createDate;
    private Role roles;
}
