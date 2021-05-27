package com.kj.springsecurity.user;

import com.kj.springsecurity.user.Role;
import com.kj.springsecurity.user.User;
import com.kj.springsecurity.user.UserRepository;
import com.kj.springsecurity.user.UserRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
        userRepository.save(user);
    }

    public User findCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String userName = currentUser.getName();
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new UsernameNotFoundException("Username " + userName + " not found");
    }

}
