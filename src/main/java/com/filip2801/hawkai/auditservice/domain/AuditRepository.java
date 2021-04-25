package com.filip2801.hawkai.auditservice.domain;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface AuditRepository extends Repository<AuditEntity, Long> {

    void save(AuditEntity audit);

    List<AuditEntity> findAll();

}
