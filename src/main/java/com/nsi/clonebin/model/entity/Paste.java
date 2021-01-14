package com.nsi.clonebin.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "paste")
@Data
@NoArgsConstructor
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
    private LocalDateTime createdAt;
    
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public Paste(UUID userId, UUID folderId, String title, String content, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.userId = userId;
        this.folderId = folderId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
    
}
