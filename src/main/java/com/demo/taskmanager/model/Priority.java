package com.demo.taskmanager.model;

public enum Priority {
    LOW(1, "Low Priority"),
    MEDIUM(2, "Medium Priority"), 
    HIGH(3, "High Priority");
    
    private final int level;
    private final String description;
    
    Priority(int level, String description) {
        this.level = level;
        this.description = description;
    }
    
    public int getLevel() {
        return level;
    }
    
    public String getDescription() {
        return description;
    }
    
    /**
     * Get priority by level for sorting
     */
    public static Priority fromLevel(int level) {
        for (Priority priority : Priority.values()) {
            if (priority.getLevel() == level) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Invalid priority level: " + level);
    }
}
