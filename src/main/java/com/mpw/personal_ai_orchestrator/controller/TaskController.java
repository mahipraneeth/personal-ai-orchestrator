package com.mpw.personal_ai_orchestrator.controller;

import com.mpw.personal_ai_orchestrator.dto.ChatRequest;
import com.mpw.personal_ai_orchestrator.dto.TaskCreateRequest;
import com.mpw.personal_ai_orchestrator.dto.TaskResponse;
import com.mpw.personal_ai_orchestrator.dto.UpdateTaskStatusRequest;
import com.mpw.personal_ai_orchestrator.service.AssistantTaskCreationService;
import com.mpw.personal_ai_orchestrator.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    private final AssistantTaskCreationService assistantTaskCreationService;

    public TaskController(TaskService taskService, AssistantTaskCreationService assistantTaskCreationService) {
        this.taskService = taskService;
        this.assistantTaskCreationService = assistantTaskCreationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(
            @Valid @RequestBody TaskCreateRequest request) {
        return taskService.create(request);
    }

    @GetMapping
    public List<TaskResponse> findAll() {
        return taskService.findAll();
    }

    @PatchMapping("/{id}/status")
    public TaskResponse updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskStatusRequest request) {
        return taskService.updateStatus(id, request.status());
    }

    @PostMapping("/from-message")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createFromMessage(
            @Valid @RequestBody ChatRequest request) {
        return assistantTaskCreationService.createFromMessage(
                request.message());
    }
}
