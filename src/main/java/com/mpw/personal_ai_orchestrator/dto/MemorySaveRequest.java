package com.mpw.personal_ai_orchestrator.dto;

import com.mpw.personal_ai_orchestrator.entity.MemoryCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MemorySaveRequest(@NotBlank(message = "Memory key is required")
                                 @Size(max = 150)
                                 String memoryKey,

                                @NotBlank(message = "Memory content is required")
                                 @Size(max = 2000)
                                 String content,

                                @NotNull(message = "Memory category is required")
                                MemoryCategory category) {
}
