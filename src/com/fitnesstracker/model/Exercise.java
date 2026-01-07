package com.fitnesstracker.model;

/**
 * Exercise - Abstract base class for all exercise types
 * 
 * Serves as the base for Factory Pattern implementation
 * Uses Template Method pattern for calorie calculation
 * @version 1.0
 */
public abstract class Exercise {
    
    protected String name;
    protected int duration; // in minutes
    protected int intensity; // scale 1-10
    protected String exerciseType;
    
    /**
     * Constructor for Exercise
     * 
     * @param name name of the exercise
     * @param duration duration in minutes
     * @param exerciseType type of exercise
     */
    public Exercise(String name, int duration, String exerciseType) {
        this.name = name;
        this.duration = duration;
        this.exerciseType = exerciseType;
        this.intensity = 5; // default moderate intensity
    }
    
    /**
     * Abstract method to calculate calories burned
     * Each exercise type implements its own calculation
     * Template Method Pattern - defines algorithm structure
     * 
     * @return calories burned
     */
    public abstract double calculateCalories();
    
    /**
     * Get exercise description
     * Template Method Pattern - optional hook method
     * 
     * @return description of the exercise
     */
    public String getDescription() {
        return String.format("%s (%s) - %d minutes, Intensity: %d/10", 
            name, exerciseType, duration, intensity);
    }
    
    // Getters and Setters
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public int getIntensity() {
        return intensity;
    }
    
    public void setIntensity(int intensity) {
        if (intensity < 1 || intensity > 10) {
            throw new IllegalArgumentException("Intensity must be between 1 and 10");
        }
        this.intensity = intensity;
    }
    
    public String getExerciseType() {
        return exerciseType;
    }
}