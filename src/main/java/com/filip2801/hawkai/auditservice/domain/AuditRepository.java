package com.filip2801.hawkai.auditservice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface AuditRepository extends Repository<AuditLog, Long> {

    AuditLog save(AuditLog audit);

    List<AuditLog> findAll();

    Page<AuditLog> findAll(Specification<AuditLog> specification, Pageable pageable);

}
