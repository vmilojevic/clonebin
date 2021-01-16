package com.nsi.clonebin.controller;

import com.nsi.clonebin.model.dto.FolderDTO;
import com.nsi.clonebin.model.dto.PasteDTO;
import com.nsi.clonebin.service.FolderService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @Autowired
    private FolderService folderService;


    @RequestMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index.html");
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            List<FolderDTO> folders = folderService.getFoldersForUser();
            modelAndView.addObject("foldersList", folders);
        }
        modelAndView.addObject("paste", new PasteDTO());
        return modelAndView;
    }

    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }
}
