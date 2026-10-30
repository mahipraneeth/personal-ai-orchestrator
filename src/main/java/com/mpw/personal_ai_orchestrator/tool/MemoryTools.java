package com.mpw.personal_ai_orchestrator.tool;

import com.mpw.personal_ai_orchestrator.dto.MemoryResponse;
import com.mpw.personal_ai_orchestrator.dto.MemorySaveRequest;
import com.mpw.personal_ai_orchestrator.entity.MemoryCategory;
import com.mpw.personal_ai_orchestrator.service.UserMemoryService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemoryTools {
    private final UserMemoryService memoryService;

    public MemoryTools(UserMemoryService memoryService) {
        this.memoryService = memoryService;
    }

    @Tool(description = """
            Save or update a long-term user memory.
            Use this only when the user explicitly asks you to remember,
            save, or update information for future conversations.
            Choose a concise, descriptive, reusable memory key.
            Category must be one of GOAL, PREFERENCE, PROJECT, ROUTINE, OTHER.
            If a memory already exists with different content, do not overwrite it.
            Ask the user whether they want to update the existing memory.
            Use updateMemory only after explicit confirmation.
            Do not invent information or save assumptions.
            """)
    public MemoryResponse saveMemory(
            String memoryKey,
            String content,
            String category) {

        if (memoryKey == null || memoryKey.isBlank()) {
            throw new IllegalArgumentException("Memory key is required");
        }

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Memory content is required");
        }

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("Memory category is required");
        }

        MemoryCategory memoryCategory;

        try {
            memoryCategory = MemoryCategory.valueOf(
                    category.trim().toUpperCase()
            );
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException(
                    "Invalid category. Use GOAL, PREFERENCE, PROJECT, ROUTINE or OTHER"
            );
        }

        return memoryService.saveOrUpdate(
                new MemorySaveRequest(
                        memoryKey.trim(),
                        content.trim(),
                        memoryCategory
                )
        );
    }

    @Tool(description = """
        Retrieve one specific saved long-term memory using its key.
        Use this when the user asks you to recall a particular saved fact.
        Do not invent a memory if the requested key does not exist.
        """)
    public MemoryResponse findMemory(String memoryKey) {
        return memoryService.findByKey(memoryKey);
    }

    @Tool(description = """
        Retrieve all saved long-term user memories.
        Use this when the user asks what you remember about them
        or requests a review of their saved information.
        """)
    public List<MemoryResponse> listMemories() {
        return memoryService.findAll();
    }

    @Tool(description = """
    Delete a specific saved long-term memory using its exact memory key.
    Use this only when the user explicitly asks to forget or delete
    a saved memory.
    Never delete memories based on assumptions or indirect suggestions.
    If the requested memory is unclear, ask the user which memory
    they want deleted.
    """)
    public String deleteMemory(String memoryKey) {

        if (memoryKey == null || memoryKey.isBlank()) {
            throw new IllegalArgumentException("Memory key is required");
        }

        memoryService.deleteByKey(memoryKey.trim());

        return "Memory deleted successfully: " + memoryKey;
    }

    @Tool(description = """
    Explicitly update an existing long-term memory.
    Use this only when the user clearly asks to change or update
    previously saved information.
    Requires the existing memory key and the new content.
    Never use this for creating a new memory.
    """)
    public MemoryResponse updateMemory(
            String memoryKey,
            String content,
            String category) {

        if (memoryKey == null || memoryKey.isBlank()
                || content == null || content.isBlank()
                || category == null || category.isBlank()) {
            throw new IllegalArgumentException(
                    "Memory key, content and category are required"
            );
        }

        MemoryCategory memoryCategory;

        try {
            memoryCategory = MemoryCategory.valueOf(
                    category.trim().toUpperCase()
            );
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid memory category");
        }

        return memoryService.updateMemory(
                new MemorySaveRequest(
                        memoryKey.trim(),
                        content.trim(),
                        memoryCategory
                )
        );
    }
}
