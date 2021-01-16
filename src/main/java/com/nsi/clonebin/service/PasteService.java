package com.nsi.clonebin.service;

import com.nsi.clonebin.model.dto.PasteDTO;
import com.nsi.clonebin.model.entity.Paste;
import com.nsi.clonebin.model.enums.PasteExpiringEnum;
import com.nsi.clonebin.repository.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PasteService {

    private final PasteRepository pasteRepository;

    @Autowired
    public PasteService(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    @Transactional(readOnly = true)
    public List<PasteDTO> getByUserId(UUID userId) {
        List<Paste> pastes = pasteRepository.findAllByUserId(userId);
        if (CollectionUtils.isEmpty(pastes)) {
            return new ArrayList<>();
        }
        return pastes.stream().map(PasteDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public Paste save(PasteDTO pasteDTO) {
        if (pasteDTO.getId() != null && pasteRepository.existsById(pasteDTO.getId())) {
            return updatePaste(pasteDTO);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = null;
        if (pasteDTO.getExpiresIn() != PasteExpiringEnum.NEVER) {
            expiresAt = now.plus(pasteDTO.getExpiresIn().getValue(), pasteDTO.getExpiresIn().getChronoUnit());
        }

        Paste newPaste = new Paste(pasteDTO.getUserId(), pasteDTO.getFolderId(), pasteDTO.getTitle(), pasteDTO.getContent(), now, expiresAt);

        return pasteRepository.save(newPaste);
    }

    private Paste updatePaste(PasteDTO pasteDTO) {

        Paste paste = pasteRepository.findById(pasteDTO.getId()).get();

        paste.setContent(pasteDTO.getContent());
        paste.setTitle(pasteDTO.getTitle());
        paste.setFolderId(pasteDTO.getFolderId());

        if (pasteDTO.getExpiresIn() != PasteExpiringEnum.NEVER) {
            LocalDateTime expiresAt = paste.getCreatedAt().plus(pasteDTO.getExpiresIn().getValue(), pasteDTO.getExpiresIn().getChronoUnit());
            paste.setExpiresAt(expiresAt);
        }
        paste.setContent(pasteDTO.getContent());

        return pasteRepository.save(paste);
    }
}
