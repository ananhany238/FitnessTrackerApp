package com.fitnesstracker.strategy;

import com.fitnesstracker.model.Exercise;
import java.util.List;

/**
 * WorkoutStrategy - Strategy Pattern Interface
 * 
 * Purpose: Defines different workout planning strategies
 * 
 * Pattern: Strategy Pattern
 * 
 * Justification:
 * - Encapsulates different workout algorithms
 * - Allows runtime selection of workout strategies
 * - Makes workout planning flexible and extensible
 * - Separates workout logic from client code
 * - Easy to add new workout strategies without modifying existing code
 * 
 * Use Cases:
 * - Beginner vs Advanced workout planning
 * - Different training splits (Full body, Upper/Lower, Push/Pull/Legs)
 * - Goal-specific workout recommendations
 * @version 1.0
 */
public interface WorkoutStrategy {
    
    /**
     * Generate a workout plan based on the strategy
     * 
     * @param duration total workout duration in minutes
     * @param focus the focus area (e.g., "CARDIO", "STRENGTH", "MIXED")
     * @return list of recommended exercises
     */
    List<Exercise> generateWorkout(int duration, String focus);
    
    /**
     * Get the name of this strategy
     * 
     * @return strategy name
     */
    String getStrategyName();
    
    /**
     * Get description of this strategy
     * 
     * @return strategy description
     */
    String getDescription();
}