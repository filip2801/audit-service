package com.filip2801.hawkai.auditservice.domain;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void log(AuditDto auditDto) {
        var auditEntity = new AuditEntity(auditDto, "" + auditDto.hashCode());
        auditRepository.save(auditEntity);
    }
}
