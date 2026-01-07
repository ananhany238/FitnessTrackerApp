package com.fitnesstracker.decorator;

import com.fitnesstracker.model.Exercise;

/**
 * ExerciseDecorator - Decorator Pattern Base Class
 * 
 * Purpose: Allows dynamic addition of features to exercises without modifying their classes
 * 
 * Pattern: Decorator Pattern
 * 
 * Justification:
 * - Enables adding extra features to exercises at runtime
 * - Follows Open/Closed Principle (open for extension, closed for modification)
 * - Provides flexible alternative to subclassing for extending functionality
 * - Allows stacking multiple decorators for combined features
 * - Maintains interface compatibility with base Exercise class
 * 
 * Use Cases:
 * - Add equipment tracking to exercises
 * - Add music/audio to workouts
 * - Add personal trainer guidance
 * - Add video demonstrations
 * - Combine multiple enhancements
 * @version 1.0
 */

//Decorator inherits from the same abstraction which guarantees interface compatibility with Exercise.
public abstract class ExerciseDecorator extends Exercise {
    
    
    //Holds a reference to the wrapped object
    //core requirement of the Decorator pattern.
    protected Exercise decoratedExercise;
    
    /**
     * Constructor for ExerciseDecorator
     * 
     * @param exercise the exercise to decorate
     */
    
    //Wraps an existing Exercise instance
    //decorator does not create behavior, it wraps and extends an existing object.
    public ExerciseDecorator(Exercise exercise) {
        super(exercise.getName(), exercise.getDuration(), exercise.getExerciseType());
        this.decoratedExercise = exercise;
        this.intensity = exercise.getIntensity();
    }
    
    /**
     * Calculate calories - delegates to decorated exercise
     * Can be overridden to modify calorie calculation
     * 
     * @return calories burned
     */
    @Override
    public double calculateCalories() {
        return decoratedExercise.calculateCalories();
    }
    
    /**
     * Get description - delegates to decorated exercise
     * Decorators extend this to add their own info
     * 
     * @return exercise description
     */
    @Override
    public String getDescription() {
        return decoratedExercise.getDescription();
    }
}