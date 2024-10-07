package com.company.myweb.service;

import com.company.myweb.dto.RoleDTO;
import com.company.myweb.dto.UserDTO;
import com.company.myweb.entity.Role;
import com.company.myweb.entity.User;
import com.company.myweb.payload.request.SignUpRequest;
import com.company.myweb.repository.RoleRepository;
import com.company.myweb.repository.UserRepository;
import com.company.myweb.service.interfaces.ILoginService;
import com.company.myweb.utils.ObjectUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService implements ILoginService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public LoginService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> listUser = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User users : listUser) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getId());
            userDTO.setUserName(users.getUsername());
            userDTO.setFullName(users.getFullName());
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public boolean checkLogin(String username, String password) {
        User user = userRepository.findByUserName(username);
        return passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean addUser(SignUpRequest signUpRequest) {
        Optional<Role> roleByRoleName = roleRepository.findRoleByRoleName(signUpRequest.getRoleName());
        Role role = roleByRoleName.get();

        User user = new User();
        user.setFullName(signUpRequest.getFullName());
        user.setUserName(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRoles(role);
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDTO getUserByUserName(String userName) {
        User user = this.userRepository.findByUserName(userName);
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getUsername());
            userDTO.setFullName(user.getFullName());
            userDTO.setRoles(ObjectUtil.copyProperties(user.getRoles(), new RoleDTO(), RoleDTO.class, true));
            return userDTO;
        } else {
            return null;
        }
    }
}
