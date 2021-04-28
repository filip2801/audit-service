package com.filip2801.hawkai.auditservice.domain;

import com.filip2801.hawkai.auditservice.controller.AuditLogsFilter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public AuditEntity log(AuditDto auditDto) {
        var lastHash = lastHashRepository.find();
        var newHash = calculateNewHash(auditDto, lastHash.getHash());
        var auditEntity = new AuditEntity(auditDto, newHash);
        var createdAuditLog = auditRepository.save(auditEntity);

        lastHash.changeHash(newHash);
        lastHashRepository.save(lastHash);

        return createdAuditLog;
    }

    private String calculateNewHash(AuditDto auditDto, String lastHash) {
        var stringToHash = auditDto.getMessage() + lastHash;
        return DigestUtils.sha256Hex(stringToHash);
    }

    public Page<AuditEntity> findAll(AuditLogsFilter filter, Pageable pageable) {
        return auditRepository.findAll(new AuditEntitySpecification(filter), pageable);
    }
}
