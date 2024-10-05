package com.company.myweb.service;

import com.company.myweb.dto.UserDTO;
import com.company.myweb.entity.Role;
import com.company.myweb.entity.Users;
import com.company.myweb.payload.request.SignUpRequest;
import com.company.myweb.repository.UserRepository;
import com.company.myweb.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService implements LoginServiceImp {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public LoginService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<Users> listUser = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (Users users : listUser) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getId());
            userDTO.setUserName(users.getUserName());
            userDTO.setFullName(users.getFullName());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public boolean checkLogin(String username, String password) {
        Users user = userRepository.findByUserName(username);
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean addUser(SignUpRequest signUpRequest) {
        Role roles = new Role();
        roles.setId(signUpRequest.getRoleId());
        Users user = new Users();
        user.setFullName(signUpRequest.getFullname());
        user.setUserName(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setRoles(roles);
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDTO getUserByUserName(String userName) {
        Users user = this.userRepository.findByUserName(userName);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getUserName());
            userDTO.setPassword(user.getPassword());
            userDTO.setFullName(user.getFullName());
            return userDTO;
        } else {
            return null;
        }
    }
}
