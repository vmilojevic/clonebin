package com.nsi.clonebin.repository;

import com.nsi.clonebin.model.entity.Folder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FolderRepository extends JpaRepository<Folder, UUID> {

    List<Folder> findByUserId(UUID userID);
}
