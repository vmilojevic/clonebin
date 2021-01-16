package com.nsi.clonebin.model.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class FolderDTO {

    private UUID id;
    private UUID userId;
    private String name;
    private LocalDateTime createdAt;
}
