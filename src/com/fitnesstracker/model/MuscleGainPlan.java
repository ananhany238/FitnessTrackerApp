package com.fitnesstracker.model;

/**
 * MuscleGainPlan - Concrete nutrition plan for muscle building
 * 
 * Implements caloric surplus strategy with high protein and carbs
 * Part of Factory and Strategy patterns
 * @version 1.0
 */
public class MuscleGainPlan extends NutritionPlan {
    
    /**
     * Constructor for MuscleGainPlan
     * 
     * @param bmr basal metabolic rate
     * @param userWeight user's weight in kg
     */
    public MuscleGainPlan(double bmr, double userWeight) {
        super(bmr, userWeight, "Muscle Gain");
    }
    
    /**
     * Calculate macros for muscle gain
     * Strategy: Moderate caloric surplus (15% above maintenance)
     * High protein (2.0g per kg) for muscle synthesis
     * High carbs for energy and recovery
     * 
     * @param bmr basal metabolic rate
     */
    @Override
    protected void calculateMacros(double bmr) {
        // Apply activity multiplier (moderate to active)
        double maintenanceCalories = bmr * 1.55;
        
        // Create 15% caloric surplus for muscle gain
        this.dailyCalories = maintenanceCalories * 1.15;
        
        // High protein: 2.0g per kg body weight (optimal for muscle growth)
        this.proteinGrams = userWeight * 2.0;
        
        // Protein calories (4 cal per gram)
        double proteinCalories = proteinGrams * 4;
        
        // Fats: 25% of total calories (hormone production)
        double fatCalories = dailyCalories * 0.25;
        this.fatsGrams = fatCalories / 9;
        
        // Remaining calories from carbs (energy for workouts and recovery)
        double carbCalories = dailyCalories - proteinCalories - fatCalories;
        this.carbsGrams = carbCalories / 4;
    }
    
    /**
     * Get description of muscle gain plan
     * 
     * @return description
     */
    @Override
    public String getDescription() {
        return "Muscle Gain Plan: Designed for building lean muscle mass. " +
               "Features a moderate caloric surplus with high protein and carbohydrates. " +
               "Optimal for strength training and muscle growth. Target: 0.25-0.5kg gain per week.";
    }
}