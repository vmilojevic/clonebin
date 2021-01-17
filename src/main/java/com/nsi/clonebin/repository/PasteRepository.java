package com.nsi.clonebin.repository;

import com.nsi.clonebin.model.entity.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PasteRepository extends JpaRepository<Paste, UUID> {

    List<Paste> findAllByUserId(UUID userId);

    List<Paste> findAllByFolderId(UUID folderId);

    @Modifying
    Long deleteByExpiresAtBefore(LocalDateTime dateTime);
}
