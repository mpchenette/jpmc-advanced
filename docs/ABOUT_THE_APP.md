# Task Management API

A task management REST API built with Java, Maven and Spring Boot.

## Features

- ✅ **Task Management**: Create, read, update, and delete tasks
- 🎯 **Priority System**: Assign LOW, MEDIUM, or HIGH priority to tasks
- 📂 **Categories**: Organize tasks into categories (Work, Personal, etc.)
- ⏰ **Due Date Tracking**: Set and monitor task due dates
- 🔍 **Search & Filter**: Find tasks by title, description, priority, or category
- 📊 **Statistics**: Get completion rates and task analytics
- ⚡ **Zero Configuration**: H2 in-memory database requires no setup

## Quick Start

### Prerequisites - MUST ALREADY BE INSTALLED IN ORDER TO WORK
- Java 17+
- Maven 3.6+

### Running the Application

1. Clone the repository:
```bash
git clone <repository-url>
cd jpmc-advanced
```

2. Run the application:
```bash
mvn spring-boot:run
```

3. Access the API at `http://localhost:8080/api/tasks`

## API Endpoints

### Task Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks |
| GET | `/api/tasks/{id}` | Get task by ID |
| POST | `/api/tasks` | Create new task |
| PUT | `/api/tasks/{id}` | Update existing task |
| PUT | `/api/tasks/{id}/complete` | Mark task as completed |
| DELETE | `/api/tasks/{id}` | Delete task |

### Filtering & Search

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks/status/{completed}` | Get tasks by completion status |
| GET | `/api/tasks/priority/{priority}` | Get tasks by priority (LOW/MEDIUM/HIGH) |
| GET | `/api/tasks/category/{category}` | Get tasks by category |
| GET | `/api/tasks/search?query={term}` | Search tasks by title or description |
| GET | `/api/tasks/overdue` | Get overdue tasks |
| GET | `/api/tasks/due-today` | Get tasks due today |
| GET | `/api/tasks/sorted-by-priority` | Get all tasks sorted by priority |
| GET | `/api/tasks/high-priority-incomplete` | Get high priority incomplete tasks |

### Analytics

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks/statistics` | Get task completion statistics |

## Task Model

```json
{
  "id": 1,
  "title": "Complete project documentation",
  "description": "Write comprehensive README and API documentation",
  "completed": false,
  "createdDate": "2025-08-11T10:30:00",
  "dueDate": "2025-08-15T17:00:00",
  "priority": "HIGH",
  "category": "Work"
}
```

## Example API Calls

### Create a Task
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Learn Spring Boot",
    "description": "Complete Spring Boot tutorial",
    "priority": "MEDIUM",
    "category": "Learning",
    "dueDate": "2025-08-20T17:00:00"
  }'
```

### Get All Tasks
```bash
curl http://localhost:8080/api/tasks
```

### Search Tasks
```bash
curl "http://localhost:8080/api/tasks/search?query=Spring"
```

### Get High Priority Tasks
```bash
curl http://localhost:8080/api/tasks/priority/HIGH
```

### Mark Task as Completed
```bash
curl -X PUT http://localhost:8080/api/tasks/1/complete
```

### Get Task Statistics
```bash
curl http://localhost:8080/api/tasks/statistics
```

## Sample Response - Task Statistics
```json
{
  "totalTasks": 10,
  "completedTasks": 6,
  "pendingTasks": 4,
  "overdueTasks": 1,
  "completionRate": 60.0
}
```

## Priority Levels

- **LOW**: Basic tasks, can be done when time permits
- **MEDIUM**: Standard priority tasks (default)
- **HIGH**: Urgent tasks that need immediate attention

## Development

### Project Structure
```
src/main/java/com/demo/taskmanager/
├── TaskManagerApplication.java    # Main Spring Boot application
├── controller/
│   └── TaskController.java        # REST API endpoints
├── model/
│   ├── Task.java                  # Task entity
│   └── Priority.java              # Priority enum
├── repository/
│   └── TaskRepository.java        # Data access layer
└── service/
    └── TaskService.java           # Business logic
```

### Running Tests
```bash
mvn test
```

### Building the Application
```bash
mvn clean package
java -jar target/task-manager-0.0.1-SNAPSHOT.jar
```

## Technologies Used

- **Spring Boot 3.1.2**: Application framework
- **Spring Data JPA**: Data persistence
- **H2 Database**: In-memory database (zero configuration)
- **Maven**: Build tool
- **JUnit 5**: Testing framework
- **Mockito**: Mocking framework
- **Java 17**: Programming language

## Data Persistence

The application uses an embedded database that automatically initializes when the application starts. All data is stored in memory during runtime, making it perfect for development and testing without requiring any database setup or configuration.
