package com.nsi.clonebin.controller;

import com.nsi.clonebin.service.PasteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class PasteController {

    private final PasteService pasteService;

    @Autowired
    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
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
