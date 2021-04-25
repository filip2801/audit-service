package com.filip2801.hawkai.auditservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@ContextConfiguration(initializers = DbInitializer.class)
@SpringBootTest
class AuditServiceApplicationTests extends Specification {

	@Autowired
	WebApplicationContext context

	def "should start application"() {
		expect: 'application started'
		context != null
	}

}
