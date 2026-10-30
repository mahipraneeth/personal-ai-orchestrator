package com.mpw.personal_ai_orchestrator.service;

import com.mpw.personal_ai_orchestrator.dto.MemoryResponse;
import com.mpw.personal_ai_orchestrator.dto.MemorySaveRequest;
import com.mpw.personal_ai_orchestrator.entity.UserMemory;
import com.mpw.personal_ai_orchestrator.exception.MemoryAlreadyExistsException;
import com.mpw.personal_ai_orchestrator.exception.MemoryNotFoundException;
import com.mpw.personal_ai_orchestrator.repository.UserMemoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserMemoryService {
    private final UserMemoryRepository repository;

    public UserMemoryService(UserMemoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public MemoryResponse saveOrUpdate(MemorySaveRequest request) {

        String key = request.memoryKey().trim().toLowerCase();

        var existing = repository.findByMemoryKeyIgnoreCase(key);

        if (existing.isPresent()) {
            UserMemory memory = existing.get();

            if (!memory.getContent().equals(request.content().trim())
                    || memory.getCategory() != request.category()) {
                throw new MemoryAlreadyExistsException(key);
            }

            // Identical save request is safe and idempotent.
            return toResponse(memory);
        }

        UserMemory memory = new UserMemory(
                key,
                request.content().trim(),
                request.category()
        );

        return toResponse(repository.save(memory));
    }

    @Transactional
    public MemoryResponse updateMemory(MemorySaveRequest request) {

        String key = request.memoryKey().trim().toLowerCase();

        UserMemory memory = repository
                .findByMemoryKeyIgnoreCase(key)
                .orElseThrow(() -> new MemoryNotFoundException(key));

        memory.setContent(request.content().trim());
        memory.setCategory(request.category());

        return toResponse(repository.save(memory));
    }

    @Transactional(readOnly = true)
    public MemoryResponse findByKey(String memoryKey) {

        UserMemory memory = repository
                .findByMemoryKeyIgnoreCase(memoryKey.trim())
                .orElseThrow(() -> new MemoryNotFoundException(memoryKey));

        return toResponse(memory);
    }

    @Transactional(readOnly = true)
    public List<MemoryResponse> findAll() {
        return repository.findAllByOrderByUpdatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void deleteByKey(String memoryKey) {

        UserMemory memory = repository
                .findByMemoryKeyIgnoreCase(memoryKey.trim())
                .orElseThrow(() -> new MemoryNotFoundException(memoryKey));

        repository.delete(memory);
    }

    private MemoryResponse toResponse(UserMemory memory) {
        return new MemoryResponse(
                memory.getId(),
                memory.getMemoryKey(),
                memory.getContent(),
                memory.getCategory(),
                memory.getCreatedAt(),
                memory.getUpdatedAt()
        );
    }
}
