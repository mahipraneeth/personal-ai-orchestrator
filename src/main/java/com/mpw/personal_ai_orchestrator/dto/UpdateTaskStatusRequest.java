package com.mpw.personal_ai_orchestrator.dto;

import com.mpw.personal_ai_orchestrator.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(@NotNull(message = "Task status is required")
                                      TaskStatus status) {
}
