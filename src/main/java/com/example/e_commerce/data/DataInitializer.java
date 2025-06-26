package com.example.e_commerce.data;

import com.example.e_commerce.model.Role;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.RoleRepository;
import com.example.e_commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initializeRoles();
        createDefaultUsersIfNotExists();
        createDefaultAdminsIfNotExists();
    }

    private void initializeRoles() {
        createRoleIfNotExists("USER");
        createRoleIfNotExists("ADMIN");
    }

    private void createDefaultUsersIfNotExists() {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role USER not found in database!"));

        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "user" + i + "@gmail.com";
            if (!userRepository.existsByEmail(defaultEmail)) {
                saveUser("User", "User" + i, defaultEmail, "123456" + i, userRole);
            }
        }

    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

    private void createDefaultAdminsIfNotExists() {
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(() -> new RuntimeException("Role ADMIN not found in database!"));

        for (int i = 1; i <= 5; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (!userRepository.existsByEmail(defaultEmail)) {
                saveUser("Admin", "Admin" + i, defaultEmail, "123456" + i, adminRole);
            }
        }
    }

    private void saveUser(String firstName, String lastName, String email, String rawPassword, Role role) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(Set.of(role));

        userRepository.save(user);
    }



}