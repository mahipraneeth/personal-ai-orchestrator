package com.mpw.personal_ai_orchestrator.exception;

import com.mpw.personal_ai_orchestrator.dto.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex){
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");

        return ResponseEntity.badRequest().body(
                new ApiError(400, "Bad Request", message)
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMalformedJson(
            HttpMessageNotReadableException ex) {

        return ResponseEntity.badRequest().body(
                new ApiError(
                        400,
                        "Bad Request",
                        "Request body is missing or contains invalid JSON"
                )
        );
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handleTaskNotFound(
            TaskNotFoundException ex) {
        return ResponseEntity.status(404).body(
                new ApiError(404, "Not Found", ex.getMessage()));
    }

    @ExceptionHandler(InvalidGeneratedTaskException.class)
    public ResponseEntity<ApiError> handleInvalidGeneratedTask(
            InvalidGeneratedTaskException ex) {
        return ResponseEntity.status(422).body(
                new ApiError(422, "Unprocessable Entity", ex.getMessage()));
    }
}
