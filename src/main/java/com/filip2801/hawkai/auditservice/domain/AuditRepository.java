package com.filip2801.hawkai.auditservice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AuditRepository extends Repository<AuditEntity, Long> {

    void save(AuditEntity audit);

    List<AuditEntity> findAll();

    Page<AuditEntity> findAll(Specification<AuditEntity> specification, Pageable pageable);

}
