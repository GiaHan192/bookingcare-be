package com.company.myweb.service;

import com.company.myweb.dto.UserDTO;
import com.company.myweb.entity.Roles;
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
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;
    @Override
    public List<UserDTO> getAllUser(){
            List<Users> listUser = userRepository.findAll();
            List<UserDTO> userDTOList = new ArrayList<>();
            for (Users users: listUser) {
                UserDTO userDTO = new UserDTO();
                userDTO.setId(users.getId());
                userDTO.setUserName(users.getUserName());
                userDTO.setPassword(users.getPassword());
                userDTO.setFullname(users.getFullName());

                userDTOList.add(userDTO);
            }
            return userDTOList;
    }

    @Override
    public boolean checkLogin(String username,String password){

        Users user = userRepository.findByUserName(username);
        return passwordEncoder.matches(password,user.getPassword());
    }
    @Override
    public boolean addUser(SignUpRequest signUpRequest){
        Roles roles = new Roles();
        roles.setId(signUpRequest.getRoleId());

        Users users = new Users();
        users.setFullName(signUpRequest.getFullname());
        users.setUserName(signUpRequest.getEmail());
        users.setPassword(signUpRequest.getPassword());
        users.setRoles(roles);

        try {
            userRepository.save(users);
            return true;
        }catch (Exception e){
            return false;
        }

    }
}
