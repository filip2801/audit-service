package com.filip2801.hawkai.auditservice.domain;

import com.filip2801.hawkai.auditservice.controller.AuditLogsFilter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuditLogService {

    private final AuditRepository auditRepository;
    private final LastHashRepository lastHashRepository;

    public AuditLogService(AuditRepository auditRepository, LastHashRepository lastHashRepository) {
        this.auditRepository = auditRepository;
        this.lastHashRepository = lastHashRepository;
    }

    @Transactional
    public AuditLog createLog(AuditLogDto auditLogDto) {
        var lastHash = lastHashRepository.find();
        var auditLog = new AuditLog(auditLogDto, lastHash.getHash());
        var createdAuditLog = auditRepository.save(auditLog);

        lastHash.changeHash(auditLog.getHash());
        lastHashRepository.save(lastHash);

        return createdAuditLog;
    }

    public Page<AuditLog> findAll(AuditLogsFilter filter, Pageable pageable) {
        return auditRepository.findAll(new AuditLogSpecification(filter), pageable);
    }
}
