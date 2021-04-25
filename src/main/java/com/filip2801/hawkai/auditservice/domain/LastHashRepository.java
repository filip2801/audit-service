package com.filip2801.hawkai.auditservice.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface LastHashRepository extends Repository<LastHash, Long> {

    @Query(value = "select id, hash from last_hash LIMIT 1 for update", nativeQuery = true)
    LastHash find();

    void save(LastHash lastHash);

}
