package com.todeb.bkolay.creditapplication;

import com.todeb.bkolay.creditapplication.model.entity.User;
import com.todeb.bkolay.creditapplication.repository.UserRepository;
import com.todeb.bkolay.creditapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SampleDataInitiliazer implements ApplicationRunner {

    //private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Creating a sample Admin USER
        User adminUser = new User("admin-user", "adminuser@mail.com", "pass1234");
        if (!userRepository.existsByUsername(adminUser.getUsername())) {
            userService.signup(adminUser, true);
        }
        // Creating a sample USER
        User sampleUser = new User("sample-user", "sampleuser@mail.com", "pass1234");
        if (!userRepository.existsByUsername(sampleUser.getUsername())) {
            userService.signup(sampleUser, false);
        }

    }
}
