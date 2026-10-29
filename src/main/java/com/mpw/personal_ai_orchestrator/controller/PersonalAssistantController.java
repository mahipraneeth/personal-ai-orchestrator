package com.mpw.personal_ai_orchestrator.controller;

import com.mpw.personal_ai_orchestrator.dto.ChatRequest;
import com.mpw.personal_ai_orchestrator.dto.ChatResponse;
import com.mpw.personal_ai_orchestrator.dto.DailyPlan;
import com.mpw.personal_ai_orchestrator.service.PersonalAssistantService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assistant")
public class PersonalAssistantController {

    private final PersonalAssistantService personalAssistantService;

    public PersonalAssistantController(PersonalAssistantService personalAssistantService) {
        this.personalAssistantService = personalAssistantService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@Valid @RequestBody ChatRequest request){
        return personalAssistantService.chat(request);
    }

    @PostMapping("/plan")
    public DailyPlan plan(@Valid @RequestBody ChatRequest request){
        return personalAssistantService.createPlan(request);
    }
}
