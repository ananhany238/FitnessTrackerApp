package com.fitnesstracker.model;

/**
 * CardioExercise - Concrete implementation of Exercise for cardiovascular activities
 * 
 * Purpose:
 * Represents cardiovascular exercises that elevate heart rate and improve
 * cardiovascular fitness. Part of the Factory Pattern implementation where
 * ExerciseFactory creates instances based on "CARDIO" type.
 * 
 * Design Pattern: Factory Pattern (Product)
 * 
 * Examples of Cardio Exercises:
 * - Running / Jogging
 * - Cycling / Spinning
 * - Swimming
 * - Rowing
 * - Jump Rope
 * - Dancing
 * - Aerobics
 * 
 * Calorie Burn Rate:
 * Base rate of 8.0 calories per minute at moderate intensity (5/10).
 * This is based on average metabolic equivalents (METs) for cardio activities.
 * 
 * Formula: 8.0 × duration × (intensity / 5.0)
 * @version 1.0
 */
public class CardioExercise extends Exercise {
    
    /**
     * Base calories burned per minute at moderate intensity (5/10)
     * Based on average METs (Metabolic Equivalent of Task) for cardio activities
     * Reference: American College of Sports Medicine (ACSM)
     */
    private static final double BASE_CALORIES_PER_MINUTE = 8.0;
    
    /**
     * Constructor for CardioExercise
     * 
     * Creates a new cardiovascular exercise with specified parameters.
     * Automatically sets the exercise type to "CARDIO".
     * 
     * @param name the name of the cardio exercise (e.g., "Running", "Cycling")
     * @param duration the duration of the exercise in minutes
     */
    public CardioExercise(String name, int duration) {
        super(name, duration, "CARDIO");
    }
    
    /**
     * Calculate calories burned for cardio exercise
     * 
     * Implementation of the abstract method from Exercise class.
     * Uses intensity as a multiplier where 5 (moderate) is the baseline.
     * 
     * Formula: BASE_CALORIES_PER_MINUTE × duration × (intensity / 5.0)
     * 
     * Intensity Effects:
     * - intensity < 5: Burns fewer calories (e.g., walking)
     * - intensity = 5: Moderate burn (e.g., jogging)
     * - intensity > 5: Burns more calories (e.g., sprinting)
     * 
     * @return the total calories burned as a double
     */
    @Override
    public double calculateCalories() {
        double intensityMultiplier = intensity / 5.0;
        return BASE_CALORIES_PER_MINUTE * duration * intensityMultiplier;
    }
    
    /**
     * Get enhanced description with calorie information
     * 
     * Overrides the base description to include estimated calories burned,
     * which is particularly relevant for cardio exercises where calorie
     * burn is a primary metric.
     * 
     * @return detailed description including calorie estimate
     */
    @Override
    public String getDescription() {
        return super.getDescription() + 
               String.format(" - Burns ~%.0f calories", calculateCalories());
    }
}