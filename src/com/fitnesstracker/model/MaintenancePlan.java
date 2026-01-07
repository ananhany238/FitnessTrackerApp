package com.fitnesstracker.model;

/**
 * MaintenancePlan - Concrete nutrition plan for weight maintenance
 * 
 * Implements balanced nutrition strategy
 * Part of Factory and Strategy patterns
 * 
 * @author Fitness Tracker Team
 * @version 1.0
 */
public class MaintenancePlan extends NutritionPlan {
    
    /**
     * Constructor for MaintenancePlan
     * 
     * @param bmr basal metabolic rate
     * @param userWeight user's weight in kg
     */
    public MaintenancePlan(double bmr, double userWeight) {
        super(bmr, userWeight, "Maintenance");
    }
    
    /**
     * Calculate macros for maintenance
     * Strategy: Maintenance calories with balanced macros
     * Moderate protein, carbs, and fats
     * 
     * @param bmr basal metabolic rate
     */
    @Override
    protected void calculateMacros(double bmr) {
        // Apply activity multiplier (moderate activity)
        double maintenanceCalories = bmr * 1.55;
        
        // No caloric surplus or deficit
        this.dailyCalories = maintenanceCalories;
        
        // Moderate protein: 1.8g per kg body weight
        this.proteinGrams = userWeight * 1.8;
        
        // Protein calories (4 cal per gram)
        double proteinCalories = proteinGrams * 4;
        
        // Fats: 30% of total calories (balanced for hormones)
        double fatCalories = dailyCalories * 0.30;
        this.fatsGrams = fatCalories / 9;
        
        // Remaining calories from carbs
        double carbCalories = dailyCalories - proteinCalories - fatCalories;
        this.carbsGrams = carbCalories / 4;
    }
    
    /**
     * Get description of maintenance plan
     * 
     * @return description
     */
    @Override
    public String getDescription() {
        return "Maintenance Plan: Designed for maintaining current weight and body composition. " +
               "Features balanced macronutrients for sustained energy and health. " +
               "Ideal for general fitness and long-term lifestyle maintenance.";
    }
}