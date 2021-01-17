package com.nsi.clonebin.controller;

import com.nsi.clonebin.exception.NotFoundException;
import com.nsi.clonebin.model.dto.FolderDTO;
import com.nsi.clonebin.model.dto.MyClonebinPasteDTO;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.service.FolderService;
import com.nsi.clonebin.service.PasteService;
import com.nsi.clonebin.service.UserAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/folder")
public class FolderController {

    private final FolderService folderService;
    private final PasteService pasteService;
    private final UserAccountService userAccountService;

    public FolderController(FolderService folderService, PasteService pasteService,
                            UserAccountService userAccountService) {
        this.folderService = folderService;
        this.pasteService = pasteService;
        this.userAccountService = userAccountService;
    }

    @GetMapping("/{folderId}")
    public ModelAndView getFolder(@PathVariable("folderId") String folderId, Principal principal) {
        List<FolderDTO> folders = folderService.getFoldersForUser().stream()
                .filter(folderDTO -> folderDTO.getId().equals(UUID.fromString(folderId)))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(folders)) {
            List<MyClonebinPasteDTO> pastes = pasteService.getByFolderId(UUID.fromString(folderId));
            ModelAndView modelAndView = new ModelAndView("folder");
            modelAndView.addObject("pastes", pastes);
            modelAndView.addObject("isFolder", true);
            UserAccount user = userAccountService.getById(folders.get(0).getUserId());
            modelAndView.addObject("username", user.getUsername());
            if (principal.getName().equals(user.getUsername())) {
                modelAndView.addObject("isOwner", true);
            } else {
                modelAndView.addObject("isOwner", false);
            }
            return modelAndView;
        } else {
            throw new NotFoundException("Could not find folder with id: " + folderId);
        }
    }
}
