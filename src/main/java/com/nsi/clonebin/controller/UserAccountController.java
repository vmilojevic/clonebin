package com.nsi.clonebin.controller;

import com.nsi.clonebin.exception.NotFoundException;
import com.nsi.clonebin.model.dto.FolderDTO;
import com.nsi.clonebin.model.dto.MyClonebinPasteDTO;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.service.FolderService;
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
    private final FolderService folderService;

    @Autowired
    public UserAccountController(UserAccountService userAccountService, PasteService pasteService,
                                 FolderService folderService) {
        this.userAccountService = userAccountService;
        this.pasteService = pasteService;
        this.folderService = folderService;
    }

    @GetMapping("/{username}")
    public String showRegistrationFrom(@PathVariable String username, Model model, Principal principal) {
        UserAccount userAccount = userAccountService.getByUsername(username);
        if (userAccount == null) {
            throw new NotFoundException("Could not find user with username: " + username);
        }
        model.addAttribute("username", userAccount.getUsername());
        List<MyClonebinPasteDTO> pastes = pasteService.getByUserId(userAccount.getId());
        model.addAttribute("pastes", pastes);
        List<FolderDTO> folders = folderService.getFoldersForUser();
        model.addAttribute("folders", folders);

        if (principal != null && principal.getName().equals(username)) {
            model.addAttribute("isOwner", true);
        } else {
            model.addAttribute("isOwner", false);
        }
        model.addAttribute("isFolder", false);
        return "my_clonebin";
    }
}
