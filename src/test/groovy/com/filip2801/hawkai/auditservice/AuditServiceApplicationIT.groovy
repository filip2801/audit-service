package com.filip2801.hawkai.auditservice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.WebApplicationContext

class AuditServiceApplicationIT extends IntegrationTestSpecification {

	@Autowired
	WebApplicationContext context

	def "should start application"() {
		expect: 'application started'
		context != null
	}

}
