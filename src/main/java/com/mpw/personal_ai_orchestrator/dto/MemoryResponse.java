package com.mpw.personal_ai_orchestrator.dto;

import com.mpw.personal_ai_orchestrator.entity.MemoryCategory;

import java.time.LocalDateTime;

public record MemoryResponse(Long id,
                             String memoryKey,
                             String content,
                             MemoryCategory category,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt) {
}
