package com.mpw.personal_ai_orchestrator.dto;

public record ApiError(int status, String error, String message) {
}
