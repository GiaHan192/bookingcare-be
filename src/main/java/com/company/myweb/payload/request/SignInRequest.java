package com.company.myweb.payload.request;

import lombok.Data;

@Data
public class SignInRequest {
    private String userName;
    private String password;
}
