package com.fitnesstracker.model;

/**
 * StrengthExercise - Concrete implementation of Exercise for strength training
 * 
 * Purpose:
 * Represents resistance training exercises that build muscle strength and endurance.
 * Part of the Factory Pattern where ExerciseFactory creates instances based on
 * "STRENGTH" type.
 * 
 * Design Pattern: Factory Pattern (Product)
 * 
 * Examples of Strength Exercises:
 * - Weight lifting (Bench Press, Squats, Deadlifts)
 * - Bodyweight exercises (Push-ups, Pull-ups, Dips)
 * - Resistance band training
 * - Machine exercises
 * - Free weight exercises (Dumbbells, Barbells)
 * - Calisthenics
 * 
 * Calorie Burn Rate:
 * Base rate of 6.0 calories per minute at moderate intensity (5/10).
 * Strength training typically burns fewer calories per minute than cardio
 * but provides metabolic benefits through increased muscle mass.
 * 
 * Formula: 6.0 × duration × (intensity / 5.0)
 * Note: Strength training also provides post-exercise oxygen consumption (EPOC)
 * effect, burning additional calories after the workout ends.
 * @version 1.0
 */
public class StrengthExercise extends Exercise {
    
    /**
     * Base calories burned per minute at moderate intensity (5/10)
     * Lower than cardio due to rest periods and different metabolic demands
     * Reference: American College of Sports Medicine (ACSM)
     */
    private static final double BASE_CALORIES_PER_MINUTE = 6.0;
    
    /**
     * Constructor for StrengthExercise
     * 
     * Creates a new strength training exercise with specified parameters.
     * Automatically sets the exercise type to "STRENGTH".
     * 
     * @param name the name of the strength exercise (e.g., "Bench Press", "Squats")
     * @param duration the duration of the exercise in minutes
     */
    public StrengthExercise(String name, int duration) {
        super(name, duration, "STRENGTH");
    }
    
    /**
     * Calculate calories burned for strength exercise
     * 
     * Implementation of the abstract method from Exercise class.
     * Accounts for the intermittent nature of strength training with rest periods.
     * 
     * Formula: BASE_CALORIES_PER_MINUTE × duration × (intensity / 5.0)
     * 
     * Intensity Interpretation for Strength Training:
     * - 1-3: Light weights, many reps, long rest (rehabilitation)
     * - 4-6: Moderate weights, 8-12 reps (hypertrophy range)
     * - 7-9: Heavy weights, 3-6 reps (strength building)
     * - 10: Maximum effort, 1-3 reps (power lifting)
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
     * Overrides the base description to include estimated calories burned.
     * For strength training, this represents direct calorie burn during exercise.
     * Actual metabolic impact includes post-workout calorie burn (EPOC).
     * 
     * @return detailed description including calorie estimate
     */
    @Override
    public String getDescription() {
        return super.getDescription() + 
               String.format(" - Burns ~%.0f calories", calculateCalories());
    }
}