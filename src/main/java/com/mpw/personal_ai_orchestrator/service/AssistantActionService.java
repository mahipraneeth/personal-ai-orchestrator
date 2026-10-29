package com.mpw.personal_ai_orchestrator.service;

import com.mpw.personal_ai_orchestrator.dto.ChatRequest;
import com.mpw.personal_ai_orchestrator.tool.TaskTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssistantActionService {

    private final ChatClient chatClient;
    private final TaskTools taskTools;

    public AssistantActionService(
            ChatClient.Builder builder,
            TaskTools taskTools) {
        this.chatClient = builder.build();
        this.taskTools = taskTools;
    }

    public String handle(ChatRequest request) {
        log.info("inside handle !!");
        return chatClient.prompt()
                .system("""
                You are the Personal AI Orchestrator.
                Understand the user's intent and respond helpfully.
                Only use the tools registered for answering any questions.
                If the user's intent is unclear, ask a question.
                """)
                .user(request.message())
                .tools(taskTools)
                .call()
                .content();
    }
}
