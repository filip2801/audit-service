package com.filip2801.hawkai.auditservice.domain;

import java.time.LocalDateTime;

public class AuditLogDto {

    private final String type;
    private final String subtype;
    private final LocalDateTime timestamp;
    private final String message;
    private final String username;

    public AuditLogDto(String type, String subtype, LocalDateTime timestamp, String message, String username) {
        this.type = type;
        this.subtype = subtype;
        this.timestamp = timestamp;
        this.message = message;
        this.username = username;
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
