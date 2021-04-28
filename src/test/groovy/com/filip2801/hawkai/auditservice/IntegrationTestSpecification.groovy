package com.filip2801.hawkai.auditservice

import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import javax.sql.DataSource

@ContextConfiguration(initializers = DbInitializer.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = RestTemplateTestConfig)
class IntegrationTestSpecification extends Specification {

    @Autowired
    DataSource dataSource

    void cleanup() {
        cleanDatabase()
    }

    void cleanDatabase() {
        Sql sql = new Sql(dataSource)
        sql.execute("delete from audit_log")
        sql.execute("update last_hash set hash='0'")
    }

}
