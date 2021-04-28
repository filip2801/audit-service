package com.filip2801.hawkai.auditservice.controller;

import com.filip2801.hawkai.auditservice.domain.AuditEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class AuditResource {

    private Long id;
    private final String type;
    private final String subtype;
    private final LocalDateTime timestamp;
    private final String message;
    private final String username;

    public AuditResource(String type, String subtype,
                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime timestamp,
                         String message, String username) {

        this.type = type;
        this.subtype = subtype;
        this.timestamp = timestamp;
        this.message = message;
        this.username = username;
    }

    public AuditResource(AuditEntity auditEntity) {
        this.id = auditEntity.getId();
        this.type = auditEntity.getType();
        this.subtype = auditEntity.getSubtype();
        this.timestamp = auditEntity.getTimestamp();
        this.message = auditEntity.getMessage();
        this.username = auditEntity.getUsername();
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
