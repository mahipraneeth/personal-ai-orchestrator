package com.mpw.personal_ai_orchestrator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(@NotBlank(message = "Task title must not be blank")
                                @Size(max = 200, message = "Task title must not exceed 200 characters")
                                String title,

                                @Size(max = 2000, message = "Description must not exceed 2000 characters")
                                String description) {
}
