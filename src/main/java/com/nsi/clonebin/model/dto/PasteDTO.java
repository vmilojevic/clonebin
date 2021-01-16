/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsi.clonebin.model.dto;

import com.nsi.clonebin.model.enums.PasteExpiringEnum;
import java.util.UUID;
import lombok.Data;

/**
 *
 * @author vlada
 */
@Data
public class PasteDTO {

    private UUID id;
    private UUID userId;
    private UUID folderId;
    private String title;
    private String content;
    private PasteExpiringEnum expiresIn;
}
