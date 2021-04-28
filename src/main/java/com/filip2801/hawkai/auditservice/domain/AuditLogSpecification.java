package com.filip2801.hawkai.auditservice.domain;

import com.filip2801.hawkai.auditservice.web.AuditLogsFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.Set;

public class AuditLogSpecification implements Specification<AuditLog> {

    private final AuditLogsFilter filter;

    public AuditLogSpecification(AuditLogsFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<AuditLog> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Set<Predicate> predicates = new HashSet<>();
        if (filter.getType() != null) {
            predicates.add(builder.equal(root.get("type"), filter.getType()));
        }
        if (filter.getSubtype() != null) {
            predicates.add(builder.equal(root.get("subtype"), filter.getSubtype()));
        }
        if (filter.getUsername() != null) {
            predicates.add(builder.equal(root.get("username"), filter.getUsername()));
        }
        if (filter.getDateAfter() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("timestamp"), filter.getDateAfter()));
        }
        if (filter.getDateAfter() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("timestamp"), filter.getDateBefore()));
        }
        return builder.and(predicates.toArray(Predicate[]::new));
    }
}
