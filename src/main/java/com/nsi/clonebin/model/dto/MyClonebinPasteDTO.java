package com.nsi.clonebin.model.dto;

import com.nsi.clonebin.model.entity.Paste;
import com.nsi.clonebin.util.DateTimeUtil;
import lombok.Data;

import java.util.UUID;

@Data
public class MyClonebinPasteDTO {

    private UUID id;
    private String title;
    private String createdAt;
    private String expiresAt;

    public MyClonebinPasteDTO(Paste paste) {
        this.id = paste.getId();
        this.title = paste.getTitle();
        this.createdAt = DateTimeUtil.formatLocalDateTime(paste.getCreatedAt());
        this.expiresAt = DateTimeUtil.getExpiresAtTime(paste.getExpiresAt());
    }
}
