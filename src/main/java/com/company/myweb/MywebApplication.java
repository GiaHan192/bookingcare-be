package com.company.myweb;

import com.company.myweb.entity.Role;
import com.company.myweb.entity.Users;
import com.company.myweb.repository.RoleRepository;
import com.company.myweb.repository.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableCaching
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@EnableJpaAuditing
public class MywebApplication {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MywebApplication(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(MywebApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void setInitData() {
        setInitRoles();
        setInitUser();
    }

    private void setInitRoles() {
        List<Role> allRoles = roleRepository.findAll();
        if (allRoles.isEmpty()) {
            Role adminRole = new Role();
            Role userRole = new Role();

            adminRole.setRoleName("ROLE_ADMIN");
            adminRole.setCreateDate(new Date());
            userRole.setRoleName("ROLE_USER");
            userRole.setCreateDate(new Date());

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }
    }

    private void setInitUser() {
        if (userRepository.findAll().isEmpty()) {
            Role userRole = roleRepository.findRoleByRoleName("ROLE_USER").get();
            Role adminRole = roleRepository.findRoleByRoleName("ROLE_ADMIN").get();

            Users user = new Users();
            user.setUserName("usertest@gmail.com");
            user.setPassword(passwordEncoder.encode("123456789@Ax")); // Encrypt the password
            user.setFullName("User A");
            user.setRoles(userRole);
            userRepository.save(user);

            Users admin = new Users();
            admin.setUserName("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456789@Ax"));
            admin.setFullName("Admin");
            admin.setRoles(adminRole);
            userRepository.save(admin);
        }
    }
}
