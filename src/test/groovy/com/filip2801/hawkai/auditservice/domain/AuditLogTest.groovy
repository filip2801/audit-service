package com.filip2801.hawkai.auditservice.domain

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class AuditLogTest extends Specification {

    @Unroll
    def "should create audit log"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", LocalDateTime.now(), "message", "admin")

        when:
        def auditLog = new AuditLog(dto, previousHash)

        then:
        auditLog.hash == newHash

        where:
        previousHash                                                       || newHash
        '0'                                                                || 'e45637e1101eeed641a96fa5896bd900a577f9abde3ecc59aaf6298c2a9d1444'
        'e45637e1101eeed641a96fa5896bd900a577f9abde3ecc59aaf6298c2a9d1444' || '4b97af5b1028552675c891e25dc408f713aade134acbbf95e81fbcb2d8aa67e2'
    }

    def "should not be marked as tampered when message, hash and previousHash was not changed"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", LocalDateTime.now(), "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, previousHash)

        when:
        auditLog.type = UUID.randomUUID().toString()
        auditLog.subtype = UUID.randomUUID().toString()
        auditLog.timestamp = LocalDateTime.now().plusMinutes(1)
        auditLog.username = UUID.randomUUID().toString()

        then:
        !auditLog.isTampered(previousHash)
    }

    def "should change previous hash and check that log is tampered"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", LocalDateTime.now(), "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, 'xyz')

        when:
        auditLog.previousHash = 'changed previous hash'

        then:
        auditLog.isTampered(previousHash)
    }

    def "should change message and check that log is tampered"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", LocalDateTime.now(), "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, 'xyz')

        when:
        auditLog.message = 'changed message'

        then:
        auditLog.isTampered(previousHash)
    }

    def "should change hash and check that log is tampered"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", LocalDateTime.now(), "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, 'xyz')

        when:
        auditLog.hash = 'changed hash'

        then:
        auditLog.isTampered(previousHash)
    }
}
