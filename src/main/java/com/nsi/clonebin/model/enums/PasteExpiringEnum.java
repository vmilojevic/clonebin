/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nsi.clonebin.model.enums;

/**
 *
 * @author vlada
 */
public enum PasteExpiringEnum {
    NEVER("Never", null),
    TEN_MINUTES("10 Minutes", "600000"),
    ONE_HOUR("1 Hour", "3600000"),
    ONE_WEEK("1 Week", "604800000"),
    ONE_MONTH("1 Month", "2629800000"),
    ONE_YEAR("1 Year", "31536000000");

    private final String description;
    private final String periodInMillis;

    private PasteExpiringEnum(String description, String periodInMillis) {
        this.description = description;
        this.periodInMillis = periodInMillis;
    }

    public String getDescription() {
        return description;
    }

    public Long getPeriodInMillis() {
        return Long.valueOf(periodInMillis);
    }

}
