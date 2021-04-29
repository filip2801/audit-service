package com.filip2801.hawkai.auditservice.web;

import static com.filip2801.hawkai.auditservice.web.AuditLogHealth.OK;
import static com.filip2801.hawkai.auditservice.web.AuditLogHealth.TAMPERED;

class AuditLogHealthResource {

    private final AuditLogHealth status;

    private AuditLogHealthResource(AuditLogHealth status) {
        this.status = status;
    }

    public AuditLogHealth getStatus() {
        return status;
    }

    static AuditLogHealthResource ok() {
        return new AuditLogHealthResource(OK);
    }

    static AuditLogHealthResource tampered() {
        return new AuditLogHealthResource(TAMPERED);
    }
}

enum AuditLogHealth {
    OK, TAMPERED
}