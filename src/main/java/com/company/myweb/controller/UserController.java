package com.company.myweb.controller;

import com.company.myweb.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userServiceImp;

    public UserController(IUserService userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping
    public ResponseEntity<?> getAllUser(@RequestParam(required = false) String query, Pageable pageable) {
        return new ResponseEntity<>(userServiceImp.getAllUser(query, pageable), HttpStatus.OK);
    }
}
