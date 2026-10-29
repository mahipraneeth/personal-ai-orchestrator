package com.mpw.personal_ai_orchestrator.service;

import com.mpw.personal_ai_orchestrator.dto.TaskCreateRequest;
import com.mpw.personal_ai_orchestrator.dto.TaskResponse;
import com.mpw.personal_ai_orchestrator.exception.InvalidGeneratedTaskException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AssistantTaskCreationService {

    private final ChatClient chatClient;
    private final TaskService taskService;

    public AssistantTaskCreationService(
            ChatClient.Builder builder,
            TaskService taskService) {
        this.chatClient = builder.build();
        this.taskService = taskService;
    }

    public TaskResponse createFromMessage(String message) {
        TaskCreateRequest draft = chatClient.prompt()
                .system("""
                You extract one actionable task from the user's message.
                Return a task title and an optional description.
                Keep the title concise and under 200 characters.
                Keep the description under 2000 characters.
                Do not invent deadlines, priorities, or commitments
                that the user did not provide.
                Return structured task data only.
                """)
                .user(message)
                .call()
                .entity(TaskCreateRequest.class);

        validateDraft(draft);

        // Save only after validating the model-generated data.
        return taskService.create(draft);
    }

    private void validateDraft(TaskCreateRequest draft) {
        if (draft == null || draft.title() == null
                || draft.title().isBlank()
                || draft.title().trim().length() > 200) {
            throw new InvalidGeneratedTaskException(
                    "The assistant could not generate a valid task title");
        }

        if (draft.description() != null
                && draft.description().length() > 2000) {
            throw new InvalidGeneratedTaskException(
                    "The generated task description is too long");
        }
    }
}
