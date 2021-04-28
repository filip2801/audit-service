package com.filip2801.hawkai.auditservice.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String subtype;

    @Column(name = "date")
    private LocalDateTime timestamp;
    private String message;
    private String username;
    private String hash;

    protected AuditLog() {
    }

    public AuditLog(AuditLogDto auditLogDto, String hash) {
        this.type = auditLogDto.getType();
        this.subtype = auditLogDto.getSubtype();
        this.timestamp = auditLogDto.getTimestamp();
        this.message = auditLogDto.getMessage();
        this.username = auditLogDto.getUsername();
        this.hash = hash;
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

    public String getHash() {
        return hash;
    }
}
