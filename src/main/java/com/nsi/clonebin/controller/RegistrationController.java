package com.nsi.clonebin.controller;

import com.nsi.clonebin.model.dto.UserRegistrationDTO;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private final UserAccountService userAccountService;

    @Autowired
    public RegistrationController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
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
        UserAccount userAccount = userAccountService.getByUsername(registrationDTO.getUsername());
        if (userAccount == null) {
            userAccountService.save(registrationDTO);
            return "redirect:/registration?success";
        }
        // TODO: thrown an error here and handle it in ErrorHandler
        return "redirect:/registration?duplicateUsername";
    }
}
