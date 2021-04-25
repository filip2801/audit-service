package com.filip2801.hawkai.auditservice.domain;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuditService {

    private final AuditRepository auditRepository;
    private final LastHashRepository lastHashRepository;

    public AuditService(AuditRepository auditRepository, LastHashRepository lastHashRepository) {
        this.auditRepository = auditRepository;
        this.lastHashRepository = lastHashRepository;
    }

    @Transactional
    public void log(AuditDto auditDto) {
        var lastHash = lastHashRepository.find();
        var newHash = calculateNewHash(auditDto, lastHash.getHash());
        var auditEntity = new AuditEntity(auditDto, newHash);
        auditRepository.save(auditEntity);

        lastHash.changeHash(newHash);
        lastHashRepository.save(lastHash);
    }

    private String calculateNewHash(AuditDto auditDto, String lastHash) {
        var stringToHash = auditDto.getMessage() + lastHash;
        return DigestUtils.sha256Hex(stringToHash);
    }
}
