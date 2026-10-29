package com.mpw.personal_ai_orchestrator.controller;

import com.mpw.personal_ai_orchestrator.dto.ChatRequest;
import com.mpw.personal_ai_orchestrator.dto.ChatResponse;
import com.mpw.personal_ai_orchestrator.service.AssistantActionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assistant")
public class AssistantActionController {

    private final AssistantActionService actionService;

    public AssistantActionController(AssistantActionService actionService) {
        this.actionService = actionService;
    }

    @PostMapping("/act")
    public ChatResponse act(@Valid @RequestBody ChatRequest request) {
        return new ChatResponse(actionService.handle(request));
    }
}