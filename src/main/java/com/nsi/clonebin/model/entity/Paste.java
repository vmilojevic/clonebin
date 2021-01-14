package com.nsi.clonebin.model.entity;

import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "paste")
@Data
public class Paste {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;
    
    @Column(name = "folder_id")
    private UUID folderId;

    @Column(name = "title")
    private String title;
    
    @Column(name = "content")
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp cratedAt;
    
    @Column(name = "expires_at")
    private Timestamp expiresAt;

    public Paste(UUID userId, UUID folderId, String title, String content, Timestamp cratedAt, Timestamp expiresAt) {
        this.userId = userId;
        this.folderId = folderId;
        this.title = title;
        this.content = content;
        this.cratedAt = cratedAt;
        this.expiresAt = expiresAt;
    }
    
}
