package com.kj.springsecurity.user;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String userName = currentUser.getName();
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        return optionalUser.orElseGet(null);
    }

    public List<User> findAllWithoutCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getUsername().equals(currentUser.getName()))
                .collect(Collectors.toList());
    }

    public void removeAdmin(Long id) {
        User user = userRepository.findById(id).get();
        Set<UserRole> roles = user.getRoles();
        Optional<UserRole> adminRole = roles.stream()
                .filter(role -> Role.ROLE_ADMIN.equals(role.getRole()))
                .findFirst();

        if (adminRole.isPresent()){
            roles.remove(adminRole);
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    public List<User> findAllAdmins() {
        List<User> admins = new ArrayList<>();
        List<User> all = userRepository.findAll();
        for (User user : all) {
            for (UserRole role : user.getRoles()) {
                if (role.getRole().equals(Role.ROLE_ADMIN)) {
                    admins.add(user);
                }
            }
        }
        return admins;
    }

    public List<User> findAllUsers() {
        List<User> all = userRepository.findAll();
        List<User> allUsers = new ArrayList<>();

        for (User user : all) {
            if (user.getRoles().size() == 1) {
                for (UserRole role : user.getRoles()) {
                    if (!role.getRole().equals(Role.ROLE_ADMIN)) {
                        allUsers.add(user);
                    }
                }
            }
        }
        return allUsers;
    }
}
