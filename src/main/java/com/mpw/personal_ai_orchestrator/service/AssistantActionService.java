package com.mpw.personal_ai_orchestrator.service;

import com.mpw.personal_ai_orchestrator.dto.AssistantActionRequest;
import com.mpw.personal_ai_orchestrator.dto.ChatRequest;
import com.mpw.personal_ai_orchestrator.tool.TaskTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AssistantActionService {

    private final ChatClient chatClient;
    private final TaskTools taskTools;

    public AssistantActionService(
            ChatClient.Builder builder,
            TaskTools taskTools,
            ChatMemory chatMemory) {
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
        this.taskTools = taskTools;
    }

    public String handle(AssistantActionRequest request) {
        log.info("inside handle !!");
        return chatClient.prompt()
                .system("""
                    You are the Personal AI Orchestrator.
                    Understand the user's intent and respond helpfully.
                    Use the available task tools when appropriate:
                    - Create a task only when the user asks to create one.
                    - List tasks when the user asks about saved tasks.
                    - For general questions, answer without using a tool.
                    Never claim an operation succeeded unless a tool
                    returned a successful result.
                    Do not invent task IDs, saved tasks, or tool results.
                    If the user's intent is unclear, ask a question.
                    """)
                .user(request.message())
                .tools(taskTools)
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, request.conversationId()))
                .call()
                .content();
    }
}
