package com.filip2801.hawkai.auditservice.domain;

import javax.persistence.*;

@Entity
@Table(name = "last_hash")
public class LastHash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hash;

    protected LastHash() {
    }

    public void changeHash(String newHash) {
        this.hash = newHash;
    }

    public String getHash() {
        return hash;
    }
}
