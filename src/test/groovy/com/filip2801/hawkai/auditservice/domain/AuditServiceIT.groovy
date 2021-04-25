package com.filip2801.hawkai.auditservice.domain

import com.filip2801.hawkai.auditservice.IntegrationTestSpecification
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

import java.time.LocalDateTime

class AuditServiceIT extends IntegrationTestSpecification {

    @Subject
    @Autowired
    AuditService auditService

    @Autowired
    AuditRepository auditRepository

    def "should create audit log"() {
        given:
        def message = UUID.randomUUID().toString()
        def auditDto = new AuditDto("someType", "someSubtype", LocalDateTime.now(), message, "admin")

        when:
        auditService.log(auditDto)

        then:
        def foundEntries = auditRepository.findAll()
        def savedAuditEntry = foundEntries.find { it.message == message }
        savedAuditEntry != null
        savedAuditEntry.type == auditDto.type
        savedAuditEntry.subtype == auditDto.subtype
        savedAuditEntry.timestamp == auditDto.timestamp
        savedAuditEntry.username == auditDto.username
        savedAuditEntry.hash != null
    }

}
