package com.kj.springsecurity;

import com.kj.springsecurity.user.User;
import com.kj.springsecurity.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/secure")
    public String securePage() {
        return "secure";
    }

    @GetMapping("/loginform")
    public String loginForm() {
        return "loginform";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(User user) {
        String username = user.getUsername();
        String rawPassword = user.getPassword();
        userService.registerUser(username, rawPassword);
        return "redirect:loginform";
    }
}
