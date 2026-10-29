# Personal AI Orchestrator

A personal AI assistant built with Java, Spring Boot, Spring AI, and Ollama. The project is being developed incrementally toward an intelligent orchestrator that can manage tasks, plan activities, remember conversations, and eventually coordinate specialized AI agents.

## Project Vision

The long-term goal is to build a personal AI orchestrator that can:

- Understand natural-language requests.
- Create and manage tasks using AI tool calling.
- Generate structured daily plans.
- Maintain conversational context and persistent memory.
- Track progress toward personal and professional goals.
- Adapt plans when tasks are missed or circumstances change.
- Coordinate specialized agents for areas such as fitness, career development, task management, and technology events.

These are planned capabilities; implementation is progressing milestone by milestone.

## Technology Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4.1.2
- **AI Integration:** Spring AI 2.0.2
- **Local LLM Runtime:** Ollama
- **LLMs:** DeepSeek R1 and Qwen3
- **API:** Spring MVC REST APIs
- **Database:** H2
- **Persistence:** Spring Data JPA
- **Validation:** Jakarta Bean Validation
- **Build Tool:** Maven

## Current Features

### 1. AI Chat

Send natural-language messages to the assistant and receive AI-generated responses.

### 2. Structured Daily Planning

Generate structured daily plans containing activities, estimated durations, and explanations.

### 3. Request Validation and Error Handling

- Validate incoming API requests.
- Reject blank messages and invalid inputs.
- Return consistent error responses.
- Handle validation errors and missing resources.

### 4. Persistent Task Management

Tasks are stored in an H2 database and survive application restarts.

Supported operations:

- Create a task.
- Retrieve all tasks.
- Update a task's status.
- Create a task from a natural-language message.

Task statuses:

- `TODO`
- `IN_PROGRESS`
- `COMPLETED`
- `CANCELLED`

### 5. AI Tool Calling

The assistant can invoke application tools to perform task operations.

Available tools:

- `createTask` — creates and persists a task.
- `listTasks` — retrieves saved tasks.

The assistant is instructed to use these tools when appropriate and avoid claiming an operation succeeded without a successful tool result.

## REST API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/assistant/chat` | Chat with the assistant |
| POST | `/api/assistant/plan` | Generate a structured daily plan |
| POST | `/api/assistant/act` | Let the assistant interpret a request and invoke available tools |
| POST | `/api/tasks` | Create a task |
| GET | `/api/tasks` | Retrieve all tasks |
| PATCH | `/api/tasks/{id}/status` | Update task status |
| POST | `/api/tasks/from-message` | Create a task from natural language |

### Example: Chat

`POST /api/assistant/chat`

```json
{
  "message": "Help me plan my evening."
}
```

### Example: Generate a Daily Plan

`POST /api/assistant/plan`

```json
{
  "message": "Create a realistic plan for learning system design for two hours."
}
```

### Example: AI Tool Calling

`POST /api/assistant/act`

```json
{
  "message": "Create a task to revise Java concurrency."
}
```

The assistant interprets the request and can invoke the task-creation tool.

### Example: Create a Task Directly

`POST /api/tasks`

```json
{
  "title": "Revise Java concurrency",
  "description": "Review thread safety, locks, executors, and CompletableFuture."
}
```

### Example: Update Task Status

`PATCH /api/tasks/1/status`

```json
{
  "status": "IN_PROGRESS"
}
```

## Prerequisites

Install the following:

- Java 21
- Maven
- Ollama

Download a supported local model. For example:

```bash
ollama pull deepseek-r1:latest
```

Make sure Ollama is running and the model is available before starting the application.

## Configuration

The current `application.properties` includes:

```properties
spring.application.name=personal-ai-orchestrator

spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.options.model=deepseek-r1:latest
spring.ai.ollama.chat.options.temperature=0.2

spring.datasource.url=jdbc:h2:file:./orchestrator-db
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
```

The H2 database uses a local file so task records can persist across application restarts.

## Run the Application

Clone the repository and enter the project directory:

```bash
git clone <your-repository-url>
cd personal-ai-orchestrator
```

Start Ollama, ensure the configured model is available, and run:

```bash
./mvnw spring-boot:run
```

If the Maven Wrapper is not included, use:

```bash
mvn spring-boot:run
```

The API will normally be available at `http://localhost:8080`.

## Verify the Build

Compile the project:

```bash
./mvnw clean compile
```

Run tests:

```bash
./mvnw test
```

## Development Roadmap

- [x] Spring Boot and Spring AI integration
- [x] AI chat endpoint
- [x] Structured daily planning
- [x] Request validation and global error handling
- [x] Persistent task storage using H2 and Spring Data JPA
- [x] Task management REST APIs
- [x] Natural-language task creation
- [x] AI tool calling for task creation and listing
- [x] Add the JDBC chat-memory dependency
- [ ] Implement conversation IDs and JDBC-backed conversation memory
- [ ] Test conversational recall across requests and restarts
- [ ] Improve durable handling of tool-call conversation history
- [ ] Add goal and progress tracking
- [ ] Introduce specialized agents
- [ ] Implement multi-agent orchestration
- [ ] Add adaptive planning and replanning
- [ ] Explore proactive integrations and scheduled workflows

## Development Approach

The project is built in small, verifiable milestones. Each milestone is implemented and tested before the next one begins, with a focus on reliable persistence, clear API contracts, and controlled AI actions.

## License

Add a license before distributing the project publicly.
