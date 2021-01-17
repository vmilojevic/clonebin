package com.nsi.clonebin.service;

import com.nsi.clonebin.model.dto.CreateOrEditPasteDTO;
import com.nsi.clonebin.model.dto.MyClonebinPasteDTO;
import com.nsi.clonebin.model.entity.Folder;
import com.nsi.clonebin.model.entity.Paste;
import com.nsi.clonebin.model.entity.UserAccount;
import com.nsi.clonebin.repository.PasteRepository;
import com.nsi.clonebin.security.CurrentUserService;
import com.nsi.clonebin.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PasteService {

    private final PasteRepository pasteRepository;
    private final CurrentUserService currentUserService;
    private final FolderService folderService;

    @Autowired
    public PasteService(PasteRepository pasteRepository, CurrentUserService currentUserService,
                        FolderService folderService) {
        this.pasteRepository = pasteRepository;
        this.currentUserService = currentUserService;
        this.folderService = folderService;
    }

    @Transactional(readOnly = true)
    public Paste getById(UUID id) {
        return pasteRepository.getOne(id);
    }

    @Transactional(readOnly = true)
    public List<MyClonebinPasteDTO> getByUserId(UUID userId) {
        List<Paste> pastes = pasteRepository.findAllByUserId(userId);
        if (CollectionUtils.isEmpty(pastes)) {
            return new ArrayList<>();
        }
        return pastes.stream().map(MyClonebinPasteDTO::new).collect(Collectors.toList());
    }

    public List<MyClonebinPasteDTO> getByFolderId(UUID folderId) {
        List<Paste> pastes = pasteRepository.findAllByFolderId(folderId);
        if (CollectionUtils.isEmpty(pastes)) {
            return new ArrayList<>();
        }
        return pastes.stream().map(MyClonebinPasteDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public Paste save(CreateOrEditPasteDTO createOrEditPasteDTO) {
        return createOrEditPasteDTO.getId() == null ? createPaste(createOrEditPasteDTO) : updatePaste(createOrEditPasteDTO);
    }

    @Transactional
    public void delete(UUID id) {
        Paste paste = pasteRepository.getOne(id);
        UserAccount currentUser = currentUserService.getCurrentUser();
        if (currentUser == null) {
            //TODO: throw forbidden exception
            throw new RuntimeException();
        } else if (!paste.getUserId().equals(currentUser.getId())) {
            throw new RuntimeException();
        }
        pasteRepository.delete(paste);
    }

    private Paste createPaste(CreateOrEditPasteDTO pasteDTO) {
        UserAccount currentUser = currentUserService.getCurrentUser();
        Paste paste = new Paste();
        paste.setUserId(currentUser.getId());
        if (StringUtils.hasText(pasteDTO.getTitle())) {
            paste.setTitle(pasteDTO.getTitle());
        } else {
            paste.setTitle("Untitled");
        }
        paste.setContent(pasteDTO.getContent());
        paste.setFolderId(pasteDTO.getFolderId());
        paste.setCreatedAt(LocalDateTime.now());
        paste.setExpiresAt(DateTimeUtil.calucateExpiresAt(pasteDTO.getExpiringEnum()));
        if (StringUtils.hasText(pasteDTO.getNewFolderName())) {
            Folder folder = folderService.save(pasteDTO.getNewFolderName());
            paste.setFolderId(folder.getId());
        }
        return pasteRepository.save(paste);
    }

    private Paste updatePaste(CreateOrEditPasteDTO pasteDTO) {
        Optional<Paste> pasteOptional = pasteRepository.findById(pasteDTO.getId());
        if (pasteOptional.isPresent()) {
            Paste paste = pasteOptional.get();
            paste.setContent(pasteDTO.getContent());
            if (StringUtils.hasText(pasteDTO.getTitle())) {
                paste.setTitle(pasteDTO.getTitle());
            } else {
                paste.setTitle("Untitled");
            }
            paste.setFolderId(pasteDTO.getFolderId());
            if (pasteDTO.isChangeExpiresAt()) {
                paste.setExpiresAt(DateTimeUtil.calucateExpiresAt(pasteDTO.getExpiringEnum()));
            }
            if (StringUtils.hasText(pasteDTO.getNewFolderName())) {
                Folder folder = folderService.save(pasteDTO.getNewFolderName());
                paste.setFolderId(folder.getId());
            }
            return pasteRepository.save(paste);
        } else {
            return createPaste(pasteDTO);
        }
    }
}
