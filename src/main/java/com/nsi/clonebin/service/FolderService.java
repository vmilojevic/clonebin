package com.nsi.clonebin.service;

import com.nsi.clonebin.model.dto.FolderDTO;
import com.nsi.clonebin.model.entity.Folder;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.repository.FolderRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
    
    @Transactional
    public Folder save(String folderName) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount user = userService.getByUsername(username);
        Folder newFolder = new Folder(user.getId(), folderName, LocalDateTime.now());
        return folderRepository.save(newFolder);
    }

    @Transactional(readOnly = true)
    public List<FolderDTO> getFoldersForUser() {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
            return null;
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserAccount user = userService.getByUsername(username);
        return folderRepository.findByUserId(user.getId()).stream()
                .map(f -> modelMapper.map(f, FolderDTO.class))
                .collect(Collectors.toList());
    }
}
