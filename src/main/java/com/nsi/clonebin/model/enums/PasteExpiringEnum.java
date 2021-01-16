/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsi.clonebin.model.enums;

import java.time.temporal.ChronoUnit;

/**
 *
 * @author vlada
 */
public enum PasteExpiringEnum {
    NEVER("Never", null, null),
    TEN_MINUTES("10 Minutes", 10, ChronoUnit.MINUTES),
    ONE_HOUR("1 Hour", 1, ChronoUnit.HOURS),
    ONE_WEEK("1 Week", 1, ChronoUnit.WEEKS),
    ONE_MONTH("1 Month", 1, ChronoUnit.MONTHS),
    ONE_YEAR("1 Year", 1, ChronoUnit.YEARS);

    private final String description;
    private final Integer value;
    private final ChronoUnit chronoUnit;

    private PasteExpiringEnum(String description, Integer value, ChronoUnit chronoUnit) {
        this.description = description;
        this.value = value;
        this.chronoUnit = chronoUnit;
    }

    public String getDescription() {
        return description;
    }

    public Integer getValue() {
        return value;
    }

    public ChronoUnit getChronoUnit() {
        return chronoUnit;
    }

}
