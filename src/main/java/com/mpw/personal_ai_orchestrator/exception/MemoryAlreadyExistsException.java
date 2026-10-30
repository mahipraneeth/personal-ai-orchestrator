package com.mpw.personal_ai_orchestrator.exception;

public class MemoryAlreadyExistsException extends RuntimeException {

    public MemoryAlreadyExistsException(String memoryKey) {
        super("Memory already exists with key: " + memoryKey
                + ". Explicit update is required.");
    }
}