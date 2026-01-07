package com.fitnesstracker.model;

/**
 * WeightLossPlan - Concrete nutrition plan for weight loss
 * 
 * Implements caloric deficit strategy with high protein
 * Part of Factory and Strategy patterns
 * 
 * @author Fitness Tracker Team
 * @version 1.0
 */
public class WeightLossPlan extends NutritionPlan {
    
    /**
     * Constructor for WeightLossPlan
     * 
     * @param bmr basal metabolic rate
     * @param userWeight user's weight in kg
     */
    public WeightLossPlan(double bmr, double userWeight) {
        super(bmr, userWeight, "Weight Loss");
    }
    
    /**
     * Calculate macros for weight loss
     * Strategy: Moderate caloric deficit (20% below maintenance)
     * High protein (2.2g per kg body weight) to preserve muscle
     * Moderate carbs and fats
     * 
     * @param bmr basal metabolic rate
     */
    @Override
    protected void calculateMacros(double bmr) {
        // Apply activity multiplier (sedentary to lightly active)
        double maintenanceCalories = bmr * 1.375;
        
        // Create 20% caloric deficit for weight loss
        this.dailyCalories = maintenanceCalories * 0.8;
        
        // High protein: 2.2g per kg body weight (preserve muscle during cut)
        this.proteinGrams = userWeight * 2.2;
        
        // Protein calories (4 cal per gram)
        double proteinCalories = proteinGrams * 4;
        
        // Fats: 25% of total calories (0.8-1g per kg body weight)
        double fatCalories = dailyCalories * 0.25;
        this.fatsGrams = fatCalories / 9; // 9 calories per gram of fat
        
        // Remaining calories from carbs
        double carbCalories = dailyCalories - proteinCalories - fatCalories;
        this.carbsGrams = carbCalories / 4; // 4 calories per gram of carbs
    }
    
    /**
     * Get description of weight loss plan
     * 
     * @return description
     */
    @Override
    public String getDescription() {
        return "Weight Loss Plan: Designed for fat loss while preserving muscle mass. " +
               "Features a moderate caloric deficit with high protein intake. " +
               "Recommended for sustainable weight loss of 0.5-1kg per week.";
    }
}