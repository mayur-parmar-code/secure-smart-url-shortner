package org.url.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.url.entity.UrlRecord;

import java.util.Optional;

public interface UrlRecordRepository extends JpaRepository<UrlRecord, Long> {

    Optional<UrlRecord> findUrlRecordByShortCode(String shortCode);

    Optional<Object> findByShortCode(String shortCode);
}
