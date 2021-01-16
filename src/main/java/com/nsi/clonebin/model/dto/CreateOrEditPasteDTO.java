package com.nsi.clonebin.model.dto;

import com.nsi.clonebin.model.entity.Paste;
import com.nsi.clonebin.model.enums.PasteExpiringEnum;
import com.nsi.clonebin.util.DateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateOrEditPasteDTO {

    private UUID id;
    private UUID folderId;
    private String title;
    private String content;
    private String expiresAt;
    private PasteExpiringEnum expiringEnum;
    private boolean changeExpiresAt;

    public CreateOrEditPasteDTO(Paste paste) {
        this.id = paste.getId();
        this.folderId = paste.getFolderId();
        this.title = paste.getTitle();
        this.content = paste.getContent();
        this.expiresAt = DateTimeUtil.getExpiresAtTime(paste.getExpiresAt());
        this.changeExpiresAt = false;
    }
}
