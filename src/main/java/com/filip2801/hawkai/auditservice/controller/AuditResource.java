package com.filip2801.hawkai.auditservice.controller;

import com.filip2801.hawkai.auditservice.domain.AuditEntity;

import java.time.LocalDateTime;

public class AuditResource {

    private Long id;
    private String type;
    private String subtype;
    private LocalDateTime timestamp;
    private String message;
    private String username;

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
