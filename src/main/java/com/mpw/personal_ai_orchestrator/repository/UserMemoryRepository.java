package com.mpw.personal_ai_orchestrator.repository;

import com.mpw.personal_ai_orchestrator.entity.UserMemory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserMemoryRepository
        extends JpaRepository<UserMemory, Long> {

    Optional<UserMemory> findByMemoryKeyIgnoreCase(String memoryKey);

    List<UserMemory> findAllByOrderByUpdatedAtDesc();

    boolean existsByMemoryKeyIgnoreCase(String memoryKey);
}