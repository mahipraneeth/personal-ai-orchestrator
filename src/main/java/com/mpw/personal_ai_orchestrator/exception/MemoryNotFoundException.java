package com.mpw.personal_ai_orchestrator.exception;

public class MemoryNotFoundException extends RuntimeException {
    public MemoryNotFoundException(String memoryKey) {
        super("Memory not found: " + memoryKey);
    }
}
