package com.filip2801.hawkai.auditservice.controller;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AuditLogsFilter {

    private String type;
    private String subtype;
    private String username;
    private LocalDateTime dateAfter;
    private LocalDateTime dateBefore;

    public AuditLogsFilter(String type, String subtype, String username,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateAfter,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateBefore
    ) {
        this.type = type;
        this.subtype = subtype;
        this.username = username;
        this.dateAfter = dateAfter;
        this.dateBefore = dateBefore;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getDateAfter() {
        return dateAfter;
    }

    public LocalDateTime getDateBefore() {
        return dateBefore;
    }
}
