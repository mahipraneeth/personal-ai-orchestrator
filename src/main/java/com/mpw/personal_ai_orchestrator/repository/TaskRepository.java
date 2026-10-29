package com.mpw.personal_ai_orchestrator.repository;

import com.mpw.personal_ai_orchestrator.entity.AssistantTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<AssistantTask, Long> {
}
