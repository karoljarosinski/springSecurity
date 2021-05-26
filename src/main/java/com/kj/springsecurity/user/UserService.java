package com.kj.springsecurity.user;

import com.kj.springsecurity.user.Role;
import com.kj.springsecurity.user.User;
import com.kj.springsecurity.user.UserRepository;
import com.kj.springsecurity.user.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String rawPassword) {
        User userToAdd = new User();
        userToAdd.setUsername(username);
        String encryptedPassword = passwordEncoder.encode(rawPassword);
        userToAdd.setPassword(encryptedPassword);
        List<UserRole> list = Arrays.asList(new UserRole(userToAdd, Role.ROLE_USER));

        userToAdd.setRoles(new HashSet<>(list));
        userRepository.save(userToAdd);

    }
}
