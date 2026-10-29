package com.mpw.personal_ai_orchestrator.dto;

import com.mpw.personal_ai_orchestrator.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(Long id,
                           String title,
                           String description,
                           TaskStatus status,
                           LocalDateTime createdAt) {
}
