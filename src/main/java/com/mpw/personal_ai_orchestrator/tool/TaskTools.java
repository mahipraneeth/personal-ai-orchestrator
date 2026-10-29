package com.mpw.personal_ai_orchestrator.tool;

import com.mpw.personal_ai_orchestrator.dto.TaskCreateRequest;
import com.mpw.personal_ai_orchestrator.dto.TaskResponse;
import com.mpw.personal_ai_orchestrator.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TaskTools {

    private final TaskService taskService;

    public TaskTools(TaskService taskService) {
        this.taskService = taskService;
    }

    @Tool(description = """
        Create and persist one task when the user explicitly asks
        to create, add, or remember a task. Requires a concise title
        and an optional description. Do not invent deadlines.
        """)
    public TaskResponse createTask(String title, String description){
        log.info("createTask tool get called with title {} and description {}", title, description);
        if (title == null || title.isBlank() || title.trim().length() > 200) {
            throw new IllegalArgumentException("Task title must be between 1 and 200 characters");
        }

        if (description != null && description.length() > 2000) {
            throw new IllegalArgumentException("Description must not exceed 2000 characters");
        }

        return taskService.create(
                new TaskCreateRequest(title.trim(), description)
        );
    }


    @Tool(description = """
        Retrieve all saved tasks when the user asks to see, list,
        review, or check their existing tasks. This tool does not
        create, update, or delete tasks.
        """)
    public List<TaskResponse> listTasks(){
        log.info("listTasks tool get called");
        return taskService.findAll();
    }

    @Tool(description = "Get current weather for a specified location")
    public String getWeather(@ToolParam(description = "City name, e.g., London, Tokyo") String city) {
        // Integrate with a external API or database here
        log.info("getWeather tool get called with city {}", city);
        return "The weather in " + city + " is sunny with 22°C.";
    }
}
