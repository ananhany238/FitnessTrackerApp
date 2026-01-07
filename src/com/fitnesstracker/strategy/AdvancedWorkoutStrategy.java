package com.fitnesstracker.strategy;

import com.fitnesstracker.factory.ExerciseFactory;
import com.fitnesstracker.model.Exercise;
import java.util.ArrayList;
import java.util.List;

/**
 * AdvancedWorkoutStrategy - Concrete Strategy
 * 
 * Generates high-intensity workouts for experienced athletes
 * Part of Strategy Pattern implementation
 * @version 1.0
 */
public class AdvancedWorkoutStrategy implements WorkoutStrategy {
    
    /**
     * Generate advanced workout plan
     * High intensity, complex movements, minimal rest
     * 
     * @param duration total duration in minutes
     * @param focus workout focus area
     * @return list of exercises
     */
    @Override
    public List<Exercise> generateWorkout(int duration, String focus) {
        List<Exercise> workout = new ArrayList<>();
        
        if (focus.equalsIgnoreCase("CARDIO")) {
            // Advanced HIIT cardio
            int intervals = duration / 5;
            for (int i = 0; i < intervals; i++) {
                workout.add(ExerciseFactory.createExercise("CARDIO", "Sprint Intervals", 
                    5, 9));
            }
            
        } else if (focus.equalsIgnoreCase("STRENGTH")) {
            // Advanced strength training - compound movements
            int timePerExercise = duration / 5;
            workout.add(ExerciseFactory.createExercise("STRENGTH", "Barbell Squats", 
                timePerExercise, 8));
            workout.add(ExerciseFactory.createExercise("STRENGTH", "Deadlifts", 
                timePerExercise, 8));
            workout.add(ExerciseFactory.createExercise("STRENGTH", "Bench Press", 
                timePerExercise, 8));
            workout.add(ExerciseFactory.createExercise("STRENGTH", "Pull-ups", 
                timePerExercise, 8));
            workout.add(ExerciseFactory.createExercise("STRENGTH", "Overhead Press", 
                timePerExercise, 8));
            
        } else {
            // Mixed advanced workout - circuit training
            int circuitRounds = duration / 15;
            for (int i = 0; i < circuitRounds; i++) {
                workout.add(ExerciseFactory.createExercise("CARDIO", "Burpees", 3, 9));
                workout.add(ExerciseFactory.createExercise("STRENGTH", "Weighted Squats", 4, 8));
                workout.add(ExerciseFactory.createExercise("CARDIO", "Mountain Climbers", 3, 9));
                workout.add(ExerciseFactory.createExercise("STRENGTH", "Push-ups", 3, 8));
                workout.add(ExerciseFactory.createExercise("FLEXIBILITY", "Dynamic Stretch", 2, 6));
            }
        }
        
        return workout;
    }
    
    @Override
    public String getStrategyName() {
        return "Advanced Performance";
    }
    
    @Override
    public String getDescription() {
        return "High-intensity workouts with complex movements and minimal rest. " +
               "Designed for experienced athletes seeking maximum performance gains.";
    }
}