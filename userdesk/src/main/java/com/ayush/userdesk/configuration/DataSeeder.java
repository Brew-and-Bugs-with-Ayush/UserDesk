package com.ayush.userdesk.configuration;

import com.ayush.userdesk.domain.entity.User;
import com.ayush.userdesk.enums.Role;
import com.ayush.userdesk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataSeeder {

    @Value("${app.default.admin.email}")
    private String email;

    @Value("${app.default.admin.password}")
    private String password;

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin() {

        return args -> {
            boolean adminExists = userRepo.findByRolesContaining(Role.ADMIN).isPresent();

            if (!adminExists) {
                User admin = new User();
                admin.setUsername("superadmin");
                admin.setEmail(email);
                admin.setPassword(passwordEncoder.encode(password));
                admin.setRoles(Set.of(Role.ADMIN));

                userRepo.save(admin);
                DataSeeder.log.info("✅ Default admin created");
            } else {
                DataSeeder.log.info("ℹ️ Admin already exists, skipping seeding.");
            }
        };
    }
}
