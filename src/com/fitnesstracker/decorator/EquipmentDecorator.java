package com.fitnesstracker.decorator;

import com.fitnesstracker.model.Exercise;

/**
 * EquipmentDecorator - Concrete Decorator
 * 
 * Adds equipment tracking to exercises
 * Part of Decorator Pattern implementation
 * @version 1.0
 */
public class EquipmentDecorator extends ExerciseDecorator {
    
    private String equipment;
    private double weightUsed; // in kg
    
    /**
     * Constructor for EquipmentDecorator
     * 
     * @param exercise the exercise to decorate
     * @param equipment the equipment used
     */
    public EquipmentDecorator(Exercise exercise, String equipment) {
        super(exercise);
        this.equipment = equipment;
        this.weightUsed = 0;
    }
    
    /**
     * Constructor with weight information
     * 
     * @param exercise the exercise to decorate
     * @param equipment the equipment used
     * @param weightUsed weight used in kg
     */
    public EquipmentDecorator(Exercise exercise, String equipment, double weightUsed) {
        super(exercise);
        this.equipment = equipment;
        this.weightUsed = weightUsed;
    }
    
    /**
     * Enhanced description including equipment info
     * 
     * @return description with equipment details
     */
    @Override
    public String getDescription() {
        String baseDescription = decoratedExercise.getDescription();
        if (weightUsed > 0) {
            return baseDescription + String.format(" | Equipment: %s (%.1f kg)", equipment, weightUsed);
        }
        return baseDescription + " | Equipment: " + equipment;
    }
    
    /**
     * Calculate calories with equipment bonus
     * Using equipment (especially weights) can increase calorie burn slightly
     * 
     * @return adjusted calories
     */
    @Override
    public double calculateCalories() {
        double baseCalories = decoratedExercise.calculateCalories();
        // 5% bonus for using equipment
        return baseCalories * 1.05;
    }
    
    public String getEquipment() {
        return equipment;
    }
    
    public double getWeightUsed() {
        return weightUsed;
    }
}