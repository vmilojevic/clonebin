package com.nsi.clonebin.service;

import com.nsi.clonebin.model.dto.PasteDTO;
import com.nsi.clonebin.model.entity.Paste;
import com.nsi.clonebin.repository.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
        List<Paste> pastes = pasteRepository.getAllByUserId(userId);
        if (CollectionUtils.isEmpty(pastes)) {
            return new ArrayList<>();
        }
        return pastes.stream().map(PasteDTO::new).collect(Collectors.toList());
    }
}
