package com.game.store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.game.store.models.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
