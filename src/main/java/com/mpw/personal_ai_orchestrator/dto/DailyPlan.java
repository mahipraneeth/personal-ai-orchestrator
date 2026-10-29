package com.mpw.personal_ai_orchestrator.dto;

import java.util.List;

public record DailyPlan(String summary, List<ActivityPlan> activities) {
}
