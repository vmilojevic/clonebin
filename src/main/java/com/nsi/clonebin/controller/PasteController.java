/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsi.clonebin.controller;

import com.nsi.clonebin.model.dto.FolderDTO;
import com.nsi.clonebin.model.dto.PasteDTO;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.service.FolderService;
import com.nsi.clonebin.service.PasteService;
import com.nsi.clonebin.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author vlada
 */
@Controller
public class PasteController {

    @Autowired
    private UserService userService;

    @Autowired
    private FolderService folderService;

    @Autowired
    private PasteService pasteService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/createPaste")
    public ModelAndView pasteSubmit(@ModelAttribute PasteDTO paste) {
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserAccount user = userService.getByUsername(username);
            paste.setUserId(user.getId());
        }

        pasteService.save(paste);

        ModelAndView modelAndView = new ModelAndView("index.html");
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            List<FolderDTO> folders = folderService.getFoldersForUser().stream()
                    .map(f -> modelMapper.map(f, FolderDTO.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("foldersList", folders);
        }
        modelAndView.addObject("paste", new PasteDTO());
        return modelAndView;
    }

    @RequestMapping(value = "/editPaste")
    public ModelAndView editPaste(@ModelAttribute PasteDTO paste) {
        ModelAndView modelAndView = new ModelAndView("edit_paste.html");

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount user = userService.getByUsername(username);
        paste.setUserId(user.getId());
        List<FolderDTO> folders = folderService.getFoldersForUser();
        modelAndView.addObject("foldersList", folders);
        modelAndView.addObject("paste", paste);

        return modelAndView;
    }
}
