package com.company.myweb.payload.request;

import lombok.Data;

@Data
public class SignUpRequest {
    private String fullName;
    private String email;
    private String password;
    private String roleName;
}
