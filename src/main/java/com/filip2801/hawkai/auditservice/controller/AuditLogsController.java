package com.filip2801.hawkai.auditservice.controller;

import com.filip2801.hawkai.auditservice.domain.AuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auditlogs")
public class AuditLogsController {

    private final AuditService auditService;

    public AuditLogsController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    Page<AuditResource> findAuditLogs(AuditLogsFilter filter, Pageable pageable) {
        return auditService.findAll(filter, pageable).map(AuditResource::new);
    }
}
