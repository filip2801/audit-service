package com.filip2801.hawkai.auditservice.controller;

import com.filip2801.hawkai.auditservice.domain.AuditDto;
import com.filip2801.hawkai.auditservice.domain.AuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auditlogs")
public class AuditLogsController {

    private final AuditService auditService;

    public AuditLogsController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    AuditResource createAuditLog(@RequestBody AuditResource resource) {
        var dto = new AuditDto(resource.getType(), resource.getSubtype(), resource.getTimestamp(), resource.getMessage(), resource.getUsername());
        var createdAuditLog = auditService.log(dto);

        return new AuditResource(createdAuditLog);
    }

    @GetMapping
    Page<AuditResource> findAuditLogs(AuditLogsFilter filter, Pageable pageable) {
        return auditService.findAll(filter, pageable).map(AuditResource::new);
    }
}
