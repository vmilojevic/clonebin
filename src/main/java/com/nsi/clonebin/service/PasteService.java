package com.nsi.clonebin.service;

import com.nsi.clonebin.model.entity.Paste;
import com.nsi.clonebin.model.dto.PasteDTO;
import com.nsi.clonebin.model.enums.PasteExpiringEnum;
import com.nsi.clonebin.repository.PasteRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasteService {

    @Autowired
    private PasteRepository pasteRepository;

    @Transactional
    public Paste save(PasteDTO pasteDTO) {
        if (pasteDTO.getId() != null && pasteRepository.existsById(pasteDTO.getId())) {
            return updatePaste(pasteDTO);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresAt = null;
        if (pasteDTO.getExpiresIn() != PasteExpiringEnum.NEVER) {
            expiresAt = now.plus(pasteDTO.getExpiresIn().getPeriodInMillis(), ChronoUnit.MILLIS);
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
            LocalDateTime expiresAt = paste.getCreatedAt().plus(pasteDTO.getExpiresIn().getPeriodInMillis(), ChronoUnit.MILLIS);
            paste.setExpiresAt(expiresAt);
        }
        paste.setContent(pasteDTO.getContent());

        return pasteRepository.save(paste);
    }
}
