package com.kj.springsecurity.admin;

import com.kj.springsecurity.user.Role;
import com.kj.springsecurity.user.User;
import com.kj.springsecurity.user.UserRole;
import com.kj.springsecurity.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String adminPanel(Model model) {
        List<User> userList = userService.findAllUsers();
//        List<User> userList = userService.findAllWithoutCurrentUser();
        model.addAttribute("users", userList);
        List<User> admins = userService.findAllAdmins();
        model.addAttribute("admins", admins);
        return "admin";
    }

    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User userToEdit = userService.findById(id);
        model.addAttribute("userToEdit", userToEdit);
        return "editUser";
    }

    @PostMapping("/editUser/{id}")
    public String editUser(@PathVariable Long id, User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/removeAdmin/{id}")
    public String removeAdmin(@PathVariable Long id){
        userService.removeAdmin(id);
        return "redirect:/admin";
    }
}
