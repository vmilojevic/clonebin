package com.nsi.clonebin.repository;

import com.nsi.clonebin.model.entity.Paste;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface PasteRepository extends JpaRepository<Paste, UUID> {

    @Modifying
    Long deleteByExpiresAtBefore(LocalDateTime dateTime);
}
