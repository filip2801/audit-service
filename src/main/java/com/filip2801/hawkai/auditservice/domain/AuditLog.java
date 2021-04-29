package com.filip2801.hawkai.auditservice.domain;

import org.apache.commons.codec.digest.DigestUtils;

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
    private String previousHash;

    protected AuditLog() {
    }

    public AuditLog(AuditLogDto auditLogDto, String previousHash) {
        this.type = auditLogDto.getType();
        this.subtype = auditLogDto.getSubtype();
        this.timestamp = auditLogDto.getTimestamp();
        this.message = auditLogDto.getMessage();
        this.username = auditLogDto.getUsername();
        this.previousHash = previousHash;
        this.hash = calculateHash(message, previousHash);
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

    public String getPreviousHash() {
        return previousHash;
    }

    public boolean isTampered(String previousHashToCheck) {
        return !this.previousHash.equals(previousHashToCheck)
                || !this.hash.equals(calculateHash(this.message, previousHashToCheck));
    }

    private String calculateHash(String message, String previousHash) {
        var stringToHash = message + previousHash;
        return DigestUtils.sha256Hex(stringToHash);
    }
}
