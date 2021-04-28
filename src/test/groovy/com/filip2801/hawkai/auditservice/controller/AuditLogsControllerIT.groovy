package com.filip2801.hawkai.auditservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.filip2801.hawkai.auditservice.IntegrationTestSpecification
import com.filip2801.hawkai.auditservice.domain.AuditDto
import com.filip2801.hawkai.auditservice.domain.AuditService
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
    AuditService auditService

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

    def "should fetch audit logs"() {
        given:
        def type = UUID.randomUUID().toString()
        def subtype = "some sub type"
        def username = "admin"
        def dateAfter = LocalDateTime.now()
        def numberOfMatchedAuditLogs = 12
        numberOfMatchedAuditLogs.times {
            def date = dateAfter.plusMinutes(new Random().nextInt(numberOfMatchedAuditLogs))
            def auditDto = new AuditDto(type, subtype, date, UUID.randomUUID().toString(), username)
            auditService.log(auditDto)
        }

        def toEarly = new AuditDto(type, subtype, dateAfter.minusMinutes(10), UUID.randomUUID().toString(), username)
        auditService.log(toEarly)

        def toLate = new AuditDto(type, subtype, dateAfter.plusYears(10), UUID.randomUUID().toString(), username)
        auditService.log(toLate)

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

    private String getBaseUrl() {
        "http://localhost:${port}${contextPath}/auditlogs"
    }

    private LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME)
    }

    private String toString(LocalDateTime date) {
        return DateTimeFormatter.ISO_DATE_TIME.format(date)
    }
}
