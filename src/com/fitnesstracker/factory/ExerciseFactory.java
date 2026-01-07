package com.fitnesstracker.factory;

import com.fitnesstracker.model.Exercise;
import com.fitnesstracker.model.CardioExercise;
import com.fitnesstracker.model.StrengthExercise;
import com.fitnesstracker.model.FlexibilityExercise;

/**
 * ExerciseFactory - Factory Pattern Implementation
 * 
 * Purpose: Creates different types of exercise objects based on the workout type
 * 
 * Pattern: Factory Pattern
 * 
 * Justification:
 * - Encapsulates object creation logic for different exercise types
 * - Promotes loose coupling - clients don't need to know concrete classes
 * - Easy to extend with new exercise types without modifying existing code
 * - Centralizes exercise creation logic for consistency
 * - Simplifies client code by providing a single creation interface
 * 
 * Exercise Types Supported:
 * - Cardio: Running, cycling, swimming, etc.
 * - Strength: Weight training, resistance exercises
 * - Flexibility: Yoga, stretching, pilates
 * @version 1.0
 */
public class ExerciseFactory {
    
    /**
     * Factory method to create exercise objects based on type
     * Uses polymorphism to return appropriate Exercise subclass
     * 
     * @param type the type of exercise ("CARDIO", "STRENGTH", "FLEXIBILITY")
     * @param name the name of the exercise
     * @param duration the duration in minutes
     * @return Exercise object of the appropriate type
     * @throws IllegalArgumentException if exercise type is invalid
     */
    public static Exercise createExercise(String type, String name, int duration) {
        // Validate input parameters
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise type cannot be null or empty");
        }
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise name cannot be null or empty");
        }
        
        if (duration <= 0) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        
        // Convert to uppercase for case-insensitive comparison
        String exerciseType = type.trim().toUpperCase();
        
        // Create appropriate exercise object based on type
        switch (exerciseType) {
            case "CARDIO":
                return new CardioExercise(name, duration);
                
            case "STRENGTH":
                return new StrengthExercise(name, duration);
                
            case "FLEXIBILITY":
                return new FlexibilityExercise(name, duration);
                
            default:
                throw new IllegalArgumentException("Invalid exercise type: " + type + 
                    ". Valid types are: CARDIO, STRENGTH, FLEXIBILITY");
        }
    }
    
    /**
     * Overloaded factory method with additional parameters
     * Allows creation with intensity level
     * 
     * @param type the type of exercise
     * @param name the name of the exercise
     * @param duration the duration in minutes
     * @param intensity the intensity level (1-10)
     * @return Exercise object with specified intensity
     */
    public static Exercise createExercise(String type, String name, int duration, int intensity) {
        Exercise exercise = createExercise(type, name, duration);
        exercise.setIntensity(intensity);
        return exercise;
    }
    
    /**
     * Get available exercise types
     * Useful for UI dropdown menus
     * 
     * @return array of available exercise types
     */
    public static String[] getExerciseTypes() {
        return new String[]{"CARDIO", "STRENGTH", "FLEXIBILITY"};
    }
    
    /**
     * Get description of exercise type
     * Provides information about what each type includes
     * 
     * @param type the exercise type
     * @return description of the exercise type
     */
    public static String getExerciseTypeDescription(String type) {
        if (type == null) {
            return "Unknown exercise type";
        }
        
        switch (type.toUpperCase()) {
            case "CARDIO":
                return "Cardiovascular exercises that increase heart rate (running, cycling, swimming)";
            case "STRENGTH":
                return "Resistance training for building muscle strength (weight lifting, bodyweight exercises)";
            case "FLEXIBILITY":
                return "Exercises that improve flexibility and range of motion (yoga, stretching, pilates)";
            default:
                return "Unknown exercise type";
        }
    }
}