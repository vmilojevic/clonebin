package com.nsi.clonebin.service;

import com.nsi.clonebin.model.dto.FolderDTO;
import com.nsi.clonebin.model.entity.Folder;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.repository.FolderRepository;
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

    @Autowired
    public FolderService(FolderRepository folderRepository, UserAccountService userAccountService,
                         ModelMapper modelMapper) {
        this.folderRepository = folderRepository;
        this.userAccountService = userAccountService;
        this.modelMapper = modelMapper;
    }
    
    @Transactional
    public Folder save(String folderName) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount user = userAccountService.getByUsername(username);
        Folder newFolder = new Folder(user.getId(), folderName, LocalDateTime.now());
        return folderRepository.save(newFolder);
    }

    @Transactional(readOnly = true)
    public List<FolderDTO> getFoldersForUser() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount user = userAccountService.getByUsername(username);
        return folderRepository.findByUserId(user.getId()).stream()
                .map(f -> modelMapper.map(f, FolderDTO.class))
                .collect(Collectors.toList());
    }
}
