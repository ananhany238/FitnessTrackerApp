package com.fitnesstracker.model;

/**
 * FlexibilityExercise - Concrete implementation of Exercise
 * 
 * Represents flexibility exercises like yoga, stretching, pilates
 * Part of Factory Pattern implementation
 * 
 * @author Fitness Tracker Team
 * @version 1.0
 */
public class FlexibilityExercise extends Exercise {
    
    // Average calories burned per minute for flexibility training
    private static final double BASE_CALORIES_PER_MINUTE = 3.5;
    
    /**
     * Constructor for FlexibilityExercise
     * 
     * @param name name of the flexibility exercise
     * @param duration duration in minutes
     */
    public FlexibilityExercise(String name, int duration) {
        super(name, duration, "FLEXIBILITY");
    }
    
    /**
     * Calculate calories burned for flexibility exercise
     * Formula: base_calories * duration * (intensity / 5)
     * 
     * @return calories burned
     */
    @Override
    public double calculateCalories() {
        double intensityMultiplier = intensity / 5.0;
        return BASE_CALORIES_PER_MINUTE * duration * intensityMultiplier;
    }
    
    /**
     * Override getDescription to provide flexibility-specific info
     * 
     * @return detailed description
     */
    @Override
    public String getDescription() {
        return super.getDescription() + String.format(" - Burns ~%.0f calories", calculateCalories());
    }
}