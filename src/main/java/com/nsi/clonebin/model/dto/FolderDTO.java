/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsi.clonebin.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

/**
 *
 * @author vlada
 */
@Data
public class FolderDTO {

    private UUID id;
    private UUID userId;
    private String name;
    private LocalDateTime createdAt;
}
