package com.nsi.clonebin.service;

import com.nsi.clonebin.repository.PasteRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletionService {

    @Autowired
    private PasteRepository pasteRepository;

    @Scheduled(fixedDelay = 60000, initialDelay = 10000)
    @Transactional
    public void deleteExpiredPastesTask() {

        Long deletedCount = pasteRepository.deleteByExpiresAtBefore(LocalDateTime.now());
        System.out.println("Number of successfully deleted pastes: " + deletedCount);
    }
}
