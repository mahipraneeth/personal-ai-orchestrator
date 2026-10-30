package com.mpw.personal_ai_orchestrator.controller;

import com.mpw.personal_ai_orchestrator.dto.MemoryResponse;
import com.mpw.personal_ai_orchestrator.dto.MemorySaveRequest;
import com.mpw.personal_ai_orchestrator.service.UserMemoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memories")
public class UserMemoryController {

    private final UserMemoryService memoryService;

    public UserMemoryController(UserMemoryService memoryService) {
        this.memoryService = memoryService;
    }

    // Save a new memory or update an existing one
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MemoryResponse saveMemory(
            @Valid @RequestBody MemorySaveRequest request) {
        return memoryService.saveOrUpdate(request);
    }

    // Retrieve all memories
    @GetMapping
    public List<MemoryResponse> getAllMemories() {
        return memoryService.findAll();
    }

    // Retrieve memory by key
    @GetMapping("/{key}")
    public MemoryResponse getMemory(@PathVariable String key) {
        return memoryService.findByKey(key);
    }

    // Delete memory by key
    @DeleteMapping("/{key}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMemory(@PathVariable String key) {
        memoryService.deleteByKey(key);
    }
}