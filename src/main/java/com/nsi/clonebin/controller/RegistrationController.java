package com.nsi.clonebin.controller;

import com.nsi.clonebin.model.dto.UserRegistrationDTO;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userAccount")
    public UserRegistrationDTO userRegistrationDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping
    public String showRegistrationFrom() {
        return "registration";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("userAccount") UserRegistrationDTO registrationDTO) {
        UserAccount userAccount = userService.getByUsername(registrationDTO.getUsername());
        if (userAccount == null) {
            userService.save(registrationDTO);
            return "redirect:/registration?success";
        }
        return "redirect:/registration?duplicateUsername";
    }
}
