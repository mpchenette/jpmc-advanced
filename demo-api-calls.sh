#!/bin/bash

# Task Management API Demo Script
# This script demonstrates the API endpoints once the application is running

echo "=== Task Management API Demo ==="
echo "Prerequisites: Application running on http://localhost:8080"
echo ""

BASE_URL="http://localhost:8080/api/tasks"

echo "1. Creating a new task..."
echo "POST $BASE_URL"
cat << 'EOF'
{
  "title": "Learn Spring Boot",
  "description": "Complete Spring Boot tutorial with GitHub Copilot",
  "priority": "HIGH",
  "category": "Learning",
  "dueDate": "2025-08-20T17:00:00"
}
EOF
echo ""

echo "2. Creating another task..."
echo "POST $BASE_URL"
cat << 'EOF'
{
  "title": "Write documentation",
  "description": "Document the API endpoints",
  "priority": "MEDIUM",
  "category": "Work"
}
EOF
echo ""

echo "3. Get all tasks:"
echo "GET $BASE_URL"
echo ""

echo "4. Search for tasks containing 'Spring':"
echo "GET $BASE_URL/search?query=Spring"
echo ""

echo "5. Get high priority tasks:"
echo "GET $BASE_URL/priority/HIGH"
echo ""

echo "6. Get tasks by category:"
echo "GET $BASE_URL/category/Work"
echo ""

echo "7. Mark task as completed:"
echo "PUT $BASE_URL/1/complete"
echo ""

echo "8. Get task statistics:"
echo "GET $BASE_URL/statistics"
echo ""

echo "9. Get overdue tasks:"
echo "GET $BASE_URL/overdue"
echo ""

echo "=== Sample Commands with curl ==="
echo ""

echo "# Start the application first:"
echo "mvn spring-boot:run"
echo ""

echo "# Create a task:"
echo 'curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '"'"'{
    "title": "Learn Spring Boot",
    "description": "Complete Spring Boot tutorial",
    "priority": "HIGH",
    "category": "Learning"
  }'"'"
echo ""

echo "# Get all tasks:"
echo "curl http://localhost:8080/api/tasks"
echo ""

echo "# Search tasks:"
echo 'curl "http://localhost:8080/api/tasks/search?query=Spring"'
echo ""

echo "# Get high priority tasks:"
echo "curl http://localhost:8080/api/tasks/priority/HIGH"
echo ""

echo "# Mark task as completed:"
echo "curl -X PUT http://localhost:8080/api/tasks/1/complete"
echo ""

echo "# Get statistics:"
echo "curl http://localhost:8080/api/tasks/statistics"
echo ""
