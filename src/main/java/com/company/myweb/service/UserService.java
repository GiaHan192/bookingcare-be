package com.company.myweb.service;

import com.company.myweb.dto.UserDTO;
import com.company.myweb.entity.Users;
import com.company.myweb.repository.UserRepository;
import com.company.myweb.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

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
            userDTO.setFullName(users.getFullName());

            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

}
