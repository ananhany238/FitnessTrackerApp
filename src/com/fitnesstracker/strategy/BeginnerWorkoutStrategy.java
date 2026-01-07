package com.fitnesstracker.strategy;

import com.fitnesstracker.factory.ExerciseFactory;
import com.fitnesstracker.model.Exercise;
import java.util.ArrayList;
import java.util.List;

/**
 * BeginnerWorkoutStrategy - Concrete Strategy
 * 
 * Generates beginner-friendly workouts with lower intensity
 * Part of Strategy Pattern implementation
 * @version 1.0
 */
public class BeginnerWorkoutStrategy implements WorkoutStrategy {
    
    /**
     * Generate beginner workout plan
     * Focus on form, moderate intensity, adequate rest
     * 
     * @param duration total duration in minutes
     * @param focus workout focus area
     * @return list of exercises
     */
    @Override
    public List<Exercise> generateWorkout(int duration, String focus) {
        List<Exercise> workout = new ArrayList<>();
        
        if (focus.equalsIgnoreCase("CARDIO")) {
            // Beginner cardio workout
            workout.add(ExerciseFactory.createExercise("CARDIO", "Walking", 
                Math.min(20, duration), 4));
            if (duration > 20) {
                workout.add(ExerciseFactory.createExercise("FLEXIBILITY", "Light Stretching", 
                    duration - 20, 3));
            }
            
        } else if (focus.equalsIgnoreCase("STRENGTH")) {
            // Beginner strength workout - bodyweight focus
            int timePerExercise = duration / 4;
            workout.add(ExerciseFactory.createExercise("STRENGTH", "Bodyweight Squats", 
                timePerExercise, 4));
            workout.add(ExerciseFactory.createExercise("STRENGTH", "Wall Push-ups", 
                timePerExercise, 4));
            workout.add(ExerciseFactory.createExercise("STRENGTH", "Plank", 
                timePerExercise, 4));
            workout.add(ExerciseFactory.createExercise("FLEXIBILITY", "Cool Down Stretch", 
                timePerExercise, 3));
            
        } else {
            // Mixed beginner workout
            int cardioTime = duration / 2;
            int stretchTime = duration - cardioTime;
            workout.add(ExerciseFactory.createExercise("CARDIO", "Light Jogging", 
                cardioTime, 4));
            workout.add(ExerciseFactory.createExercise("FLEXIBILITY", "Full Body Stretch", 
                stretchTime, 3));
        }
        
        return workout;
    }
    
    @Override
    public String getStrategyName() {
        return "Beginner Friendly";
    }
    
    @Override
    public String getDescription() {
        return "Low-intensity workouts focusing on form and building base fitness. " +
               "Perfect for those new to exercise or returning after a break.";
    }
}