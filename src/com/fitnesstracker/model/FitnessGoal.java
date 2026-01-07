package com.fitnesstracker.model;

import java.util.Date;

public class FitnessGoal {
    
    private int id; // ID from database
    private String name;
    private String description;
    private String goalType;
    private double targetValue;
    private double currentProgress;
    private String unit;
    private Date startDate;
    private Date targetDate;
    private boolean completed;

    // Constructor without description
    public FitnessGoal(String name, String goalType, double targetValue, String unit) {
        this.name = name;
        this.goalType = goalType;
        this.targetValue = targetValue;
        this.unit = unit;
        this.startDate = new Date();
        this.currentProgress = 0.0;
        this.completed = false;
        this.description = "";
    }

    // Constructor with description
    public FitnessGoal(String name, String description, String goalType, double targetValue, String unit) {
        this(name, goalType, targetValue, unit);
        this.description = description;
    }

    public double getProgressPercentage() {
        if (targetValue == 0) return 0;
        return Math.min((currentProgress / targetValue) * 100, 100.0);
    }

    public boolean isCompleted() {
        if (!completed && currentProgress >= targetValue) {
            completed = true;
        }
        return completed;
    }

    public String getSummary() {
        return String.format("%s: %.1f/%.1f %s (%.0f%%)", 
                name, currentProgress, targetValue, unit, getProgressPercentage());
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getGoalType() { return goalType; }
    public void setGoalType(String goalType) { this.goalType = goalType; }
    public double getTargetValue() { return targetValue; }
    public void setTargetValue(double targetValue) { this.targetValue = targetValue; }
    public double getCurrentProgress() { return currentProgress; }
    public void setCurrentProgress(double currentProgress) {
        this.currentProgress = currentProgress;
        isCompleted();
    }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public Date getStartDate() { return startDate; }
    public Date getTargetDate() { return targetDate; }
    public void setTargetDate(Date targetDate) { this.targetDate = targetDate; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() { return getSummary(); }
}
