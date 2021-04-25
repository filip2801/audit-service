package com.filip2801.hawkai.auditservice

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(initializers = DbInitializer.class)
@SpringBootTest
class IntegrationTestSpecification extends Specification {

}
