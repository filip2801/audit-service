package com.filip2801.hawkai.auditservice.domain

import com.filip2801.hawkai.auditservice.IntegrationTestSpecification
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

import java.time.LocalDateTime

class AuditLogServiceIT extends IntegrationTestSpecification {

    @Subject
    @Autowired
    AuditLogService auditService

    @Autowired
    AuditRepository auditRepository
    @Autowired
    LastHashRepository lastHashRepository

    def "should create audit log"() {
        given:
        def message = UUID.randomUUID().toString()
        def auditDto = new AuditLogDto("someType", "someSubtype", LocalDateTime.now(), message, "admin")

        when:
        auditService.createLog(auditDto)

        then:
        def foundEntries = auditRepository.findAll()
        def savedAuditEntry = foundEntries.find { it.message == message }
        savedAuditEntry != null
        savedAuditEntry.type == auditDto.type
        savedAuditEntry.subtype == auditDto.subtype
        savedAuditEntry.timestamp == auditDto.timestamp
        savedAuditEntry.username == auditDto.username

        and:
        def lastHash = lastHashRepository.find()
        savedAuditEntry.hash == lastHash.hash
    }

    def "should create multiple audit logs concurrently"() {
        given:
        def numberOfEntriesToCreate = 1000
        List<String> messages = []
        numberOfEntriesToCreate.times { messages.add("some message $it") }

        when:
        messages.parallelStream().forEach {
            def auditDto = new AuditLogDto("someType", "someSubtype", LocalDateTime.now(), it, "admin")
            auditService.createLog(auditDto)
        }

        then:
        def foundEntries = auditRepository.findAll()
        foundEntries.size() >= numberOfEntriesToCreate

        def entriesSortedById = foundEntries.sort { it.id }
        for (counter in 1..<entriesSortedById.size()) {
            def previousEntry = entriesSortedById[counter - 1]
            def currentEntry = entriesSortedById[counter]

            assert currentEntry.id > previousEntry.id
            assert currentEntry.hash
            assert currentEntry.previousHash == previousEntry.hash
        }

        and:
        def lastHash = lastHashRepository.find()
        entriesSortedById.last().hash == lastHash.hash
    }

}
