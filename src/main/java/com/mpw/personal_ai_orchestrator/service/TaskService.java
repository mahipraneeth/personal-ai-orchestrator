package com.mpw.personal_ai_orchestrator.service;

import com.mpw.personal_ai_orchestrator.dto.TaskCreateRequest;
import com.mpw.personal_ai_orchestrator.dto.TaskResponse;
import com.mpw.personal_ai_orchestrator.entity.AssistantTask;
import com.mpw.personal_ai_orchestrator.entity.TaskStatus;
import com.mpw.personal_ai_orchestrator.exception.TaskNotFoundException;
import com.mpw.personal_ai_orchestrator.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskResponse create(TaskCreateRequest request) {
        AssistantTask task = new AssistantTask(
                request.title().trim(),
                request.description()
        );

        return toResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public TaskResponse updateStatus(Long id, TaskStatus status) {
        AssistantTask task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.setStatus(status);

        return toResponse(taskRepository.save(task));
    }

    private TaskResponse toResponse(AssistantTask task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }
}
