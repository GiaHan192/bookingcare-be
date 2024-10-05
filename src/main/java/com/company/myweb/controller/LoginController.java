package com.company.myweb.controller;

import com.company.myweb.dto.UserDTO;
import com.company.myweb.payload.ResponseData;
import com.company.myweb.payload.request.SignInRequest;
import com.company.myweb.payload.request.SignUpRequest;
import com.company.myweb.service.imp.LoginServiceImp;
import com.company.myweb.utils.JwtUtilsHelper;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;


@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class LoginController {
    private final LoginServiceImp loginServiceImp;
    private final JwtUtilsHelper jwtUtilsHelper;

    public LoginController(LoginServiceImp loginServiceImp, JwtUtilsHelper jwtUtilsHelper) {
        this.loginServiceImp = loginServiceImp;
        this.jwtUtilsHelper = jwtUtilsHelper;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest request) {
        ResponseData responseData = new ResponseData();


        if (loginServiceImp.checkLogin(request.getUserName(), request.getPassword())) {
            UserDTO userDto = loginServiceImp.getUserByUserName(request.getUserName());
            if (userDto != null) {
                String token = jwtUtilsHelper.generateToken(userDto);
                responseData.setData(token);
            }

        } else {
            responseData.setData("");
            responseData.setSuccess(false);
        }

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        ResponseData responseData = new ResponseData();
        responseData.setData(loginServiceImp.addUser(signUpRequest));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
