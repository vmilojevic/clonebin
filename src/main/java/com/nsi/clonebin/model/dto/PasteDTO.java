package com.nsi.clonebin.model.dto;

import com.nsi.clonebin.model.entity.Paste;
import com.nsi.clonebin.model.enums.PasteExpiringEnum;
import com.nsi.clonebin.util.DateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PasteDTO {

    private UUID id;
    private UUID userId;
    private UUID folderId;
    private String title;
    private String content;
    private String createdAt;
    private String expiresAt;
    private PasteExpiringEnum expiresIn;

    public PasteDTO(Paste paste) {
        this.id = paste.getId();
        this.userId = paste.getUserId();
        this.folderId = paste.getFolderId();
        this.title = paste.getTitle();
        this.content = paste.getContent();
        this.createdAt = DateTimeUtil.formatLocalDateTime(paste.getCreatedAt());
        this.expiresAt = DateTimeUtil.getExpiresAtTime(paste.getExpiresAt());
    }
}
