package com.filip2801.hawkai.auditservice.domain;

import com.filip2801.hawkai.auditservice.web.AuditLogsFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuditLogService {

    private static final String FIRST_HASH = "0";

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

    public boolean isLogTrailTampered() {
        var auditLogs = auditRepository.findAllByOrderById();
        if (auditLogs.isEmpty()) {
            return false;
        }

        var previousHash = FIRST_HASH;
        for (AuditLog auditLog : auditLogs) {
            if (auditLog.isTampered(previousHash)) {
                return true;
            }
            previousHash = auditLog.getHash();
        }

        return false;
    }
}
