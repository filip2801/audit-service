package com.filip2801.hawkai.auditservice.controller;

import com.filip2801.hawkai.auditservice.domain.AuditLogDto;
import com.filip2801.hawkai.auditservice.domain.AuditLogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auditlogs")
public class AuditLogsController {

    private final AuditLogService auditLogService;

    public AuditLogsController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @PostMapping
    AuditLogResource createAuditLog(@RequestBody AuditLogResource resource) {
        var dto = new AuditLogDto(resource.getType(), resource.getSubtype(), resource.getTimestamp(), resource.getMessage(), resource.getUsername());
        var createdAuditLog = auditLogService.createLog(dto);

        return new AuditLogResource(createdAuditLog);
    }

    @GetMapping
    Page<AuditLogResource> findAuditLogs(AuditLogsFilter filter, Pageable pageable) {
        return auditLogService.findAll(filter, pageable).map(AuditLogResource::new);
    }
}
