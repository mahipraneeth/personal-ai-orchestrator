package com.mpw.personal_ai_orchestrator.service;

import com.mpw.personal_ai_orchestrator.dto.ActivityPlan;
import com.mpw.personal_ai_orchestrator.dto.ChatRequest;
import com.mpw.personal_ai_orchestrator.dto.ChatResponse;
import com.mpw.personal_ai_orchestrator.dto.DailyPlan;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class PersonalAssistantService {

    private final ChatClient chatClient;

    public PersonalAssistantService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public ChatResponse chat(ChatRequest request){
        return new ChatResponse(
                chatClient.prompt().system("""
                    You are the Personal AI Assistant.

                    Help the user organize tasks, plan their day,
                    develop useful habits, and work toward goals.

                    Be practical, clear, and realistic.
                    Do not claim to have performed actions
                    that you have not actually performed.
                    """)
                .user(request.message())
                .call()
                .content());
    }


    public DailyPlan createPlan(ChatRequest chatRequest){
        DailyPlan dailyPlan = chatClient.prompt().system("""
                You are the planning assistant for a Personal AI Orchestrator.

                Create a practical plan based on the user's request.
                Return a concise summary and a list of activities.
                Each activity must have a name, duration in minutes,
                and a reason.

                Durations must be positive integers.
                Do not claim to have checked calendars or completed tasks.
                If important constraints are missing, state reasonable
                assumptions in the summary.
                """)
                .user(chatRequest.message())
                .call()
                .entity(DailyPlan.class);
        validatePlan(dailyPlan);
        return dailyPlan;
    }

    private void validatePlan(DailyPlan plan) {
        if (plan == null || plan.summary() == null
                || plan.activities() == null
                || plan.activities().isEmpty()) {
            throw new IllegalArgumentException(
                    "The generated plan is incomplete");
        }

        for (ActivityPlan activity : plan.activities()) {
            if (activity == null
                    || activity.name() == null
                    || activity.name().isBlank()
                    || activity.durationMinutes() <= 0
                    || activity.reason() == null
                    || activity.reason().isBlank()) {
                throw new IllegalArgumentException(
                        "The generated plan contains an invalid activity");
            }
        }
    }
}
