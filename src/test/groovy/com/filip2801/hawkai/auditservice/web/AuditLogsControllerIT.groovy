package com.filip2801.hawkai.auditservice.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.filip2801.hawkai.auditservice.IntegrationTestSpecification
import com.filip2801.hawkai.auditservice.domain.AuditLogDto
import com.filip2801.hawkai.auditservice.domain.AuditLogService
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.web.client.RestTemplate

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AuditLogsControllerIT extends IntegrationTestSpecification {

    @Autowired
    RestTemplate restTemplate
    @Autowired
    AuditLogService auditService

    @LocalServerPort
    int port
    @Value('${server.servlet.context-path:/}')
    String contextPath

    @Autowired
    ObjectMapper objectMapper

    def "should create audit log"() {
        given:
        def requestPayload = [
                type     : 'some type',
                subtype  : 'some subtype',
                message  : 'something happened',
                timestamp: '2020-11-25T15:30:44',
                username : 'admin'
        ]

        when:
        def response = restTemplate.postForEntity(getBaseUrl(), requestPayload, HashMap)

        then:
        response.statusCode == HttpStatus.OK
        response.body.id
        response.body.type == requestPayload.type
        response.body.subtype == requestPayload.subtype
        response.body.message == requestPayload.message
        response.body.timestamp == requestPayload.timestamp
        response.body.username == requestPayload.username
    }

    def "should fetch list with created audit log"() {
        given:
        def requestPayload = [
                type     : 'some type',
                subtype  : 'some subtype',
                message  : 'something happened',
                timestamp: '2020-11-25T15:30:44',
                username : 'admin'
        ]

        restTemplate.postForEntity(getBaseUrl(), requestPayload, HashMap)

        when:
        def auditLogsPage = restTemplate.getForObject(getBaseUrl() + "?page=0&size=10", HashMap)

        then:
        auditLogsPage.totalElements == 1
        auditLogsPage.totalPages == 1
        auditLogsPage.pageable.pageNumber == 0
        auditLogsPage.content.size() == 1

        and:
        def auditLog = auditLogsPage.content.first()
        auditLog.id
        auditLog.type == requestPayload.type
        auditLog.subtype == requestPayload.subtype
        auditLog.message == requestPayload.message
        auditLog.timestamp == requestPayload.timestamp
        auditLog.username == requestPayload.username
    }

    def "should fetch audit logs"() {
        given:
        def type = UUID.randomUUID().toString()
        def subtype = "some sub type"
        def username = "admin"
        def dateAfter = LocalDateTime.now()
        def numberOfMatchedAuditLogs = 12
        numberOfMatchedAuditLogs.times {
            def date = dateAfter.plusMinutes(new Random().nextInt(numberOfMatchedAuditLogs))
            def auditDto = new AuditLogDto(type, subtype, date, UUID.randomUUID().toString(), username)
            auditService.createLog(auditDto)
        }

        def toEarly = new AuditLogDto(type, subtype, dateAfter.minusMinutes(10), UUID.randomUUID().toString(), username)
        auditService.createLog(toEarly)

        def toLate = new AuditLogDto(type, subtype, dateAfter.plusYears(10), UUID.randomUUID().toString(), username)
        auditService.createLog(toLate)

        def dateAfterParam = toString(dateAfter)
        def dateBeforeParam = toString(dateAfter.plusMinutes(numberOfMatchedAuditLogs))

        def pageSize = 10

        when:
        def auditLogsPage = restTemplate.getForObject(getBaseUrl() +
                "?type=${type}&subtype=${subtype}&username=${username}&dateAfter=${dateAfterParam}&dateBefore=${dateBeforeParam}&" +
                "page=0&size=${pageSize}&sort=timestamp,desc", HashMap)

        then:
        auditLogsPage.totalElements == numberOfMatchedAuditLogs
        auditLogsPage.totalPages == 2
        auditLogsPage.pageable.pageNumber == 0
        auditLogsPage.content.size() == pageSize

        and: "sorted"
        def fetchedAuditLogs = auditLogsPage.content
        for (i in 1..<fetchedAuditLogs.size()) {
            assert toLocalDateTime(fetchedAuditLogs[i - 1].timestamp) >= toLocalDateTime(fetchedAuditLogs[i].timestamp)
        }

        and:
        fetchedAuditLogs.each {
            assert it.id
            assert it.type == type
            assert it.subtype == subtype
            assert it.message
            assert it.username == username
        }
    }

    def "should check that data was not tampered"() {
        given:
        5.times {
            def auditDto = new AuditLogDto("type", "subtype", LocalDateTime.now(), UUID.randomUUID().toString(), "admin")
            auditService.createLog(auditDto)
        }

        when:
        def auditLogHealth = restTemplate.getForObject(getBaseUrl() + "/health", HashMap)

        then:
        auditLogHealth.status == "OK"
    }

    def "should check that data was tampered"() {
        given:
        5.times {
            def auditDto = new AuditLogDto("type", "subtype", LocalDateTime.now(), UUID.randomUUID().toString(), "admin")
            auditService.createLog(auditDto)
        }
        updateMessageInOneOfAudiLog()

        when:
        def auditLogHealth = restTemplate.getForObject(getBaseUrl() + "/health", HashMap)

        then:
        auditLogHealth.status == "TAMPERED"
    }

    private String getBaseUrl() {
        "http://localhost:${port}${contextPath}/auditlogs"
    }

    private LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
    }

    private String toString(LocalDateTime date) {
        return DateTimeFormatter.ISO_DATE_TIME.format(date)
    }

    void updateMessageInOneOfAudiLog() {
        Sql sql = new Sql(dataSource)
        sql.execute("update audit_log set message='changed message'")
    }
}
