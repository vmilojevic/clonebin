package com.nsi.clonebin.controller;

import com.nsi.clonebin.model.dto.FolderDTO;
import com.nsi.clonebin.model.dto.PasteDTO;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.service.FolderService;
import com.nsi.clonebin.service.PasteService;
import com.nsi.clonebin.service.UserAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PasteController {

    private final UserAccountService userAccountService;
    private final FolderService folderService;
    private final PasteService pasteService;
    private final ModelMapper modelMapper;

    @Autowired
    public PasteController(UserAccountService userAccountService, FolderService folderService,
                           PasteService pasteService, ModelMapper modelMapper) {
        this.userAccountService = userAccountService;
        this.folderService = folderService;
        this.pasteService = pasteService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/createPaste")
    public ModelAndView pasteSubmit(@ModelAttribute PasteDTO paste) {
        
        ModelAndView modelAndView = new ModelAndView("index.html");
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            List<FolderDTO> folders = folderService.getFoldersForUser().stream()
                    .map(f -> modelMapper.map(f, FolderDTO.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("foldersList", folders);
        }
        
        modelAndView.addObject("paste", new PasteDTO());
        
        if(paste.getContent() == null || paste.getContent().isEmpty()){
             modelAndView.addObject("errorMessage", "You cannot create an empty paste.");
             return modelAndView;
        }
        
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserAccount user = userAccountService.getByUsername(username);
            paste.setUserId(user.getId());
        }

        pasteService.save(paste);

        
        if (paste.getId() == null) {
            modelAndView.addObject("message", "Your paste has been created successfully! :)");
        } else {
            modelAndView.addObject("message", "Your paste has been edited successfully! :)");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/editPaste")
    public ModelAndView editPaste(@ModelAttribute PasteDTO paste) {
        ModelAndView modelAndView = new ModelAndView("edit_paste.html");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount user = userAccountService.getByUsername(username);
        paste.setUserId(user.getId());
        List<FolderDTO> folders = folderService.getFoldersForUser();
        modelAndView.addObject("foldersList", folders);
        modelAndView.addObject("paste", paste);

        return modelAndView;
    }

    @RequestMapping("/edit/{pasteId}")
    public String editPaste(@PathVariable String pasteId, Principal principal) {
        // check if a user owns a paste
        // if it does, redirect him to edit page
        // else, redirect him to forbidden page
        return "index";
    }

    @RequestMapping("/delete/{pasteId}")
    public String deletePaste(@PathVariable String pasteId, Principal principal) {
        // check if a user owns a paste
        // if it does, delete the paste and return him to my_clonebin page
        // else, redirect him to forbidden page
        return "index";
    }
}
