package com.nsi.clonebin.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "folder")
@Data
@NoArgsConstructor
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Folder(UUID userId, String name, LocalDateTime createdAt) {
        this.userId = userId;
        this.name = name;
        this.createdAt = createdAt;
    }

}
