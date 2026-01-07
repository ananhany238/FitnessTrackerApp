package com.fitnesstracker.model;

/**
 * NutritionPlan - Abstract base class for nutrition plans
 * 
 * Part of Factory Pattern implementation
 * Uses Strategy Pattern for different nutrition approaches
 * @version 1.0
 */
public abstract class NutritionPlan {
    
    protected double dailyCalories;
    protected double proteinGrams;
    protected double carbsGrams;
    protected double fatsGrams;
    protected String planType;
    protected double userWeight;
    
    /**
     * Constructor for NutritionPlan
     * 
     * @param bmr basal metabolic rate
     * @param userWeight user's weight in kg
     * @param planType type of nutrition plan
     */
    public NutritionPlan(double bmr, double userWeight, String planType) {
        this.userWeight = userWeight;
        this.planType = planType;
        calculateMacros(bmr);
    }
    
    /**
     * Abstract method to calculate macronutrient distribution
     * Each plan type implements its own calculation strategy
     * Strategy Pattern implementation
     * 
     * @param bmr basal metabolic rate
     */
    protected abstract void calculateMacros(double bmr);
    
    /**
     * Get nutrition plan summary
     * 
     * @return formatted summary string
     */
    public String getSummary() {
        return String.format("%s Plan\nCalories: %.0f kcal/day\nProtein: %.0fg | Carbs: %.0fg | Fats: %.0fg",
            planType, dailyCalories, proteinGrams, carbsGrams, fatsGrams);
    }
    
    /**
     * Get detailed description of the plan
     * 
     * @return description string
     */
    public abstract String getDescription();
    
    // Getters
    
    public double getDailyCalories() {
        return dailyCalories;
    }
    
    public double getProteinGrams() {
        return proteinGrams;
    }
    
    public double getCarbsGrams() {
        return carbsGrams;
    }
    
    public double getFatsGrams() {
        return fatsGrams;
    }
    
    public String getPlanType() {
        return planType;
    }
    
    public double getUserWeight() {
        return userWeight;
    }
}