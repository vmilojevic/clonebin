package com.nsi.clonebin.controller;

import com.nsi.clonebin.model.dto.PasteDTO;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.service.PasteService;
import com.nsi.clonebin.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/u")
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final PasteService pasteService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, PasteService pasteService) {
        this.userAccountService = userAccountService;
        this.pasteService = pasteService;
    }

    @GetMapping("/{username}")
    public String showRegistrationFrom(@PathVariable String username, Model model, Principal principal) {
        UserAccount userAccount = userAccountService.getByUsername(username);
        if (userAccount == null) {
            // TODO: thrown an error here and handle it in ErrorHandler
            return "not_found";
        }
        List<PasteDTO> pastes = pasteService.getByUserId(userAccount.getId());
        model.addAttribute("pastes", pastes);

        if (principal != null && principal.getName().equals(username)) {
            model.addAttribute("isOwner", true);
        } else {
            model.addAttribute("isOwner", false);
        }
        return "my_clonebin";
    }
}
