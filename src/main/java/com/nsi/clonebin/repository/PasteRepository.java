package com.nsi.clonebin.repository;

import com.nsi.clonebin.model.entity.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PasteRepository extends JpaRepository<Paste, UUID> {

    List<Paste> getAllByUserId(UUID userId);
}
