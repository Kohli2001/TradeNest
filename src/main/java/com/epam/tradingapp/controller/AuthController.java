package com.epam.tradingapp.controller;

import com.epam.tradingapp.model.User;
import com.epam.tradingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.registerUser(user.getUsername(), user.getPassword());
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
