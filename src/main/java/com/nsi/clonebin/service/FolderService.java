package com.nsi.clonebin.service;

import com.nsi.clonebin.model.dto.FolderDTO;
import com.nsi.clonebin.model.entity.Folder;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.repository.FolderRepository;
import com.nsi.clonebin.security.CurrentUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final UserAccountService userAccountService;
    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;

    @Autowired
    public FolderService(FolderRepository folderRepository, UserAccountService userAccountService,
                         ModelMapper modelMapper, CurrentUserService currentUserService) {
        this.folderRepository = folderRepository;
        this.userAccountService = userAccountService;
        this.modelMapper = modelMapper;
        this.currentUserService = currentUserService;
    }
    
    @Transactional
    public Folder save(String folderName) {
        UserAccount currentUser = currentUserService.getCurrentUser();
        Folder newFolder = new Folder(currentUser.getId(), folderName, LocalDateTime.now());
        return folderRepository.save(newFolder);
    }

    @Transactional(readOnly = true)
    public List<FolderDTO> getFoldersForUser() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return null;
        }
        UserAccount currentUser = currentUserService.getCurrentUser();
        return folderRepository.findByUserId(currentUser.getId()).stream()
                .map(f -> modelMapper.map(f, FolderDTO.class))
                .collect(Collectors.toList());
    }
}
