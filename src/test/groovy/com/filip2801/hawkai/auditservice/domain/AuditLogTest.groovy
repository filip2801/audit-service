package com.filip2801.hawkai.auditservice.domain

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class AuditLogTest extends Specification {

    private static SOME_DATE = LocalDateTime.of(2020, 10, 15, 16, 35)

    @Unroll
    def "should create audit log"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", SOME_DATE, "message", "admin")

        when:
        def auditLog = new AuditLog(dto, previousHash)

        then:
        auditLog.hash == newHash

        where:
        previousHash                                                       || newHash
        '0'                                                                || '0dbb1cec11022a771bf64872149fdb72ecaf0a14704d8cd9f6002bfa89691a07'
        'e45637e1101eeed641a96fa5896bd900a577f9abde3ecc59aaf6298c2a9d1444' || '65027d270fa57006b91719d0ae1f34a28b8534038c81730da1fb4223a4150dc9'
    }

    def "should not be marked as tampered when nothing has changed"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", SOME_DATE, "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, previousHash)

        expect:
        !auditLog.isTampered(previousHash)
    }

    def "should change previous hash and check that log is tampered"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", SOME_DATE, "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, 'xyz')

        when:
        auditLog.previousHash = 'changed previous hash'

        then:
        auditLog.isTampered(previousHash)
    }

    def "should change type and check that log is tampered"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", SOME_DATE, "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, 'xyz')

        when:
        auditLog.type = 'changed type'

        then:
        auditLog.isTampered(previousHash)
    }

    def "should change subtype and check that log is tampered"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", SOME_DATE, "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, 'xyz')

        when:
        auditLog.subtype = 'changed subtype'

        then:
        auditLog.isTampered(previousHash)
    }

    def "should change timestamp and check that log is tampered"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", SOME_DATE, "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, 'xyz')

        when:
        auditLog.timestamp = LocalDateTime.now()

        then:
        auditLog.isTampered(previousHash)
    }

    def "should change message and check that log is tampered"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", SOME_DATE, "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, 'xyz')

        when:
        auditLog.message = 'changed message'

        then:
        auditLog.isTampered(previousHash)
    }

    def "should change username and check that log is tampered"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", SOME_DATE, "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, 'xyz')

        when:
        auditLog.username = 'changedUser'

        then:
        auditLog.isTampered(previousHash)
    }

    def "should change hash and check that log is tampered"() {
        given:
        def dto = new AuditLogDto("someType", "someSubtype", SOME_DATE, "message", "admin")
        def previousHash = 'xyz'
        def auditLog = new AuditLog(dto, 'xyz')

        when:
        auditLog.hash = 'changed hash'

        then:
        auditLog.isTampered(previousHash)
    }
}
