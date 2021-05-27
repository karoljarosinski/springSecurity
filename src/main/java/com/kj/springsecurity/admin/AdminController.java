package com.kj.springsecurity.admin;

import com.kj.springsecurity.user.User;
import com.kj.springsecurity.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String adminPanel(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("users", userList);
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
}
