package com.filip2801.hawkai.auditservice.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit")
public class AuditEntity {

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

    protected AuditEntity() {
    }

    public AuditEntity(AuditDto auditDto, String hash) {
        this.type = auditDto.getType();
        this.subtype = auditDto.getSubtype();
        this.timestamp = auditDto.getTimestamp();
        this.message = auditDto.getMessage();
        this.username = auditDto.getUsername();
        this.hash = hash;
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
