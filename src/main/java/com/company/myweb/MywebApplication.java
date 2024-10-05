package com.company.myweb;

import com.company.myweb.entity.Role;
import com.company.myweb.repository.RoleRepository;
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

import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableCaching
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class MywebApplication {
    private final RoleRepository roleRepository;

    public MywebApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(MywebApplication.class, args);
    }


    @EventListener(ApplicationReadyEvent.class)
    public void setInitData() {
        setInitRoles();
    }

    private void setInitRoles() {
        List<Role> allRoles = roleRepository.findAll();
        if (allRoles.isEmpty()) {
            Role adminRole = new Role();
            Role userRole = new Role();

            adminRole.setRoleName("Admin");
            adminRole.setCreateDate(new Date());
            userRole.setRoleName("User");
            userRole.setCreateDate(new Date());

            roleRepository.save(adminRole);
            roleRepository.save(userRole);
        }
    }
}
