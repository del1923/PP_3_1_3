package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.*;

@Component
public class Initializer implements ApplicationListener<ContextRefreshedEvent> {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public Initializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Role guestRole = new Role();
        guestRole.setRole("ROLE_GUEST");
        roleRepository.save(guestRole);

        Role userRole = new Role();
        userRole.setRole("ROLE_USER");
        roleRepository.save(userRole);

        Role adminRole = new Role();
        adminRole.setRole("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Set<Role> guestRoles = new LinkedHashSet<>();
        Collections.addAll(guestRoles, guestRole);

        Set<Role> userRoles = new LinkedHashSet<>();
        Collections.addAll(userRoles, userRole, guestRole);

        Set<Role> adminRoles = new LinkedHashSet<>();
        Collections.addAll(adminRoles, adminRole, userRole, guestRole);

        User guest = new User();
        guest.setUsername("guest");
        guest.setPassword(passwordEncoder.encode("guest"));
        guest.setName("guest");
        guest.setSurName("guest");
        guest.setAge(20);
        guest.setEmail("guest@mail.ru");
        guest.setRoles(guestRoles);
        userRepository.save(guest);

        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setName("user");
        user.setSurName("user");
        user.setAge(25);
        user.setEmail("user@mail.ru");
        user.setRoles(userRoles);
        userRepository.save(user);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setName("admin");
        admin.setSurName("admin");
        admin.setAge(30);
        admin.setEmail("admin@mail.ru");
        admin.setRoles(adminRoles);
        userRepository.save(admin);



    }


}
