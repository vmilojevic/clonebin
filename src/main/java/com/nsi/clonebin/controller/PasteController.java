package com.nsi.clonebin.controller;

import com.nsi.clonebin.exception.NotFoundException;
import com.nsi.clonebin.model.dto.CreateOrEditPasteDTO;
import com.nsi.clonebin.model.dto.FolderDTO;
import com.nsi.clonebin.model.entity.Paste;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.security.CurrentUserService;
import com.nsi.clonebin.service.FolderService;
import com.nsi.clonebin.service.PasteService;
import com.nsi.clonebin.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class PasteController {

    private final FolderService folderService;
    private final PasteService pasteService;
    private final CurrentUserService currentUserService;
    private final UserAccountService userAccountService;

    @Autowired
    public PasteController(FolderService folderService, PasteService pasteService,
                           CurrentUserService currentUserService, UserAccountService userAccountService) {
        this.folderService = folderService;
        this.pasteService = pasteService;
        this.currentUserService = currentUserService;
        this.userAccountService = userAccountService;
    }

    @GetMapping("/{pasteId}")
    public ModelAndView showPaste(@PathVariable("pasteId") String pasteId) {
        Paste paste = pasteService.getById(UUID.fromString(pasteId));
        if (paste == null) {
            throw new NotFoundException("Could not find paste with id: " + pasteId);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("paste");

        List<String> pasteLines = paste.getContent().lines().collect(Collectors.toList());
        modelAndView.addObject("lines", pasteLines);
        modelAndView.addObject("title", paste.getTitle());

        if (paste.getUserId() == null) {
            modelAndView.addObject("pasteOwner", "Guest");
        } else {
            UserAccount pasteOwner = userAccountService.getById(paste.getUserId());
            modelAndView.addObject("pasteOwner", pasteOwner.getUsername());
        }

        return modelAndView;
    }

    @PostMapping("/createOrEditPaste")
    public ModelAndView pasteSubmit(@ModelAttribute CreateOrEditPasteDTO paste) {
        ModelAndView modelAndView = new ModelAndView();
        if (!StringUtils.hasText(paste.getContent())) {
            modelAndView.setViewName("index");
            modelAndView.addObject("errorMessage", "You cannot create an empty paste.");
            return modelAndView;
        }

        Paste pasteEntity = pasteService.save(paste);
        modelAndView.setViewName("redirect:/" + pasteEntity.getId());
        if (paste.getId() == null) {
            modelAndView.addObject("message", "Your paste has been created successfully! :)");
        } else {
            modelAndView.addObject("message", "Your paste has been edited successfully! :)");
        }
        return modelAndView;
    }

    @GetMapping("/edit/{pasteId}")
    public ModelAndView editPaste(@PathVariable String pasteId) {
        Paste paste = pasteService.getById(UUID.fromString(pasteId));
        UserAccount currentUser = currentUserService.getCurrentUser();
        if (paste != null && paste.getUserId().equals(currentUser.getId())) {
            ModelAndView modelAndView = new ModelAndView("edit_paste");
            List<FolderDTO> folders = folderService.getFoldersForUser();
            modelAndView.addObject("foldersList", folders);
            CreateOrEditPasteDTO pasteDTO = new CreateOrEditPasteDTO(paste);
            modelAndView.addObject("paste", pasteDTO);
            return modelAndView;
        } else {
            throw new RuntimeException();
        }
    }

    @GetMapping("/delete/{pasteId}")
    public String deletePaste(@PathVariable String pasteId, Principal principal) {
        pasteService.delete(UUID.fromString(pasteId));
        return "redirect:/u/" + principal.getName();
    }
}
