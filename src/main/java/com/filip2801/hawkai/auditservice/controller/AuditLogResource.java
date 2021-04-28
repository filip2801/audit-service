package com.filip2801.hawkai.auditservice.controller;

import com.filip2801.hawkai.auditservice.domain.AuditLog;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AuditLogResource {

    private Long id;
    private final String type;
    private final String subtype;
    private final LocalDateTime timestamp;
    private final String message;
    private final String username;

    public AuditLogResource(String type, String subtype,
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp,
                            String message, String username) {

        this.type = type;
        this.subtype = subtype;
        this.timestamp = timestamp;
        this.message = message;
        this.username = username;
    }

    public AuditLogResource(AuditLog auditLog) {
        this.id = auditLog.getId();
        this.type = auditLog.getType();
        this.subtype = auditLog.getSubtype();
        this.timestamp = auditLog.getTimestamp();
        this.message = auditLog.getMessage();
        this.username = auditLog.getUsername();
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}
