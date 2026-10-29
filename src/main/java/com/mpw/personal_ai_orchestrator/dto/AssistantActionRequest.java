package com.mpw.personal_ai_orchestrator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AssistantActionRequest(
        @NotBlank(message = "Conversation ID must not be blank")
        @Size(max = 100, message = "Conversation ID must not exceed 100 characters")
        String conversationId,

        @NotBlank(message = "Message must not be blank")
        @Size(max = 4000, message = "Message must not exceed 4000 characters")
        String message
) {}
