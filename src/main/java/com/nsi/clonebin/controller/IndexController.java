package com.nsi.clonebin.controller;

import com.nsi.clonebin.model.dto.CreateOrEditPasteDTO;
import com.nsi.clonebin.model.dto.FolderDTO;
import com.nsi.clonebin.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

    private final FolderService folderService;

    @Autowired
    public IndexController(FolderService folderService) {
        this.folderService = folderService;
    }

    @ModelAttribute("paste")
    public CreateOrEditPasteDTO pasteDTO() {
        return new CreateOrEditPasteDTO();
    }

    @RequestMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index.html");
        // TODO: extract this check into a service since we use it in many places
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            List<FolderDTO> folders = folderService.getFoldersForUser();
            modelAndView.addObject("foldersList", folders);
        }
//        modelAndView.addObject("paste", new PasteDTO());
        return modelAndView;
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }
}
