package com.nsi.clonebin.service;

import com.nsi.clonebin.repository.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DeletionService {

    private final PasteRepository pasteRepository;

    @Autowired
    public DeletionService(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    @Transactional
    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    public void deleteExpiredPastesTask() {
        Long deletedCount = pasteRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        System.out.println("Number of successfully deleted pastes: " + deletedCount);
    }
}
