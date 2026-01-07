package com.fitnesstracker.factory;

import com.fitnesstracker.model.NutritionPlan;
import com.fitnesstracker.model.WeightLossPlan;
import com.fitnesstracker.model.MuscleGainPlan;
import com.fitnesstracker.model.MaintenancePlan;

/**
 * NutritionPlanFactory - Factory Pattern Implementation
 * 
 * Purpose: Creates different nutrition plans based on user's fitness goals
 * 
 * Pattern: Factory Pattern
 * 
 * Justification:
 * - Simplifies creation of complex nutrition plan objects
 * - Encapsulates the logic for determining appropriate calorie and macro targets
 * - Easy to add new nutrition plan types without affecting existing code
 * - Provides consistent interface for creating varied nutrition plans
 * - Reduces code duplication in nutrition plan creation
 * 
 * Plan Types Supported:
 * - Weight Loss: Caloric deficit for fat loss
 * - Muscle Gain: Caloric surplus for muscle building
 * - Maintenance: Balanced nutrition for maintaining current weight
 * @version 1.0
 */
public class NutritionPlanFactory {
    
    /**
     * Factory method to create nutrition plan based on goal type
     * Calculates appropriate calorie and macronutrient targets
     * 
     * @param planType the type of plan ("WEIGHT_LOSS", "MUSCLE_GAIN", "MAINTENANCE")
     * @param userWeight current weight in kg
     * @param userHeight height in cm
     * @param age user's age
     * @param gender user's gender ("MALE" or "FEMALE")
     * @return NutritionPlan object appropriate for the goal
     * @throws IllegalArgumentException if plan type is invalid
     */
    public static NutritionPlan createNutritionPlan(String planType, double userWeight, 
                                                     int userHeight, int age, String gender) {
        // Validate input parameters
        if (planType == null || planType.trim().isEmpty()) {
            throw new IllegalArgumentException("Plan type cannot be null or empty");
        }
        
        if (userWeight <= 0 || userHeight <= 0 || age <= 0) {
            throw new IllegalArgumentException("Weight, height, and age must be positive values");
        }
        
        // Convert to uppercase for case-insensitive comparison
        String type = planType.trim().toUpperCase();
        
        // Calculate Base Metabolic Rate (BMR) using Mifflin-St Jeor Equation
        double bmr = calculateBMR(userWeight, userHeight, age, gender);
        
        // Create appropriate nutrition plan based on type
        switch (type) {
            case "WEIGHT_LOSS":
                return new WeightLossPlan(bmr, userWeight);
                
            case "MUSCLE_GAIN":
                return new MuscleGainPlan(bmr, userWeight);
                
            case "MAINTENANCE":
                return new MaintenancePlan(bmr, userWeight);
                
            default:
                throw new IllegalArgumentException("Invalid plan type: " + planType + 
                    ". Valid types are: WEIGHT_LOSS, MUSCLE_GAIN, MAINTENANCE");
        }
    }
    
    /**
     * Simplified factory method with default values
     * Useful for quick plan creation with minimal input
     * 
     * @param planType the type of plan
     * @return NutritionPlan with default parameters
     */
    public static NutritionPlan createNutritionPlan(String planType) {
        // Use default values: 70kg, 170cm, 30 years, male
        return createNutritionPlan(planType, 70.0, 170, 30, "MALE");
    }
    
    /**
     * Calculate Basal Metabolic Rate using Mifflin-St Jeor Equation
     * This is the number of calories burned at rest
     * 
     * Formula:
     * Men: BMR = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) + 5
     * Women: BMR = (10 × weight in kg) + (6.25 × height in cm) - (5 × age in years) - 161
     * 
     * @param weight weight in kg
     * @param height height in cm
     * @param age age in years
     * @param gender "MALE" or "FEMALE"
     * @return calculated BMR
     */
    private static double calculateBMR(double weight, int height, int age, String gender) {
        double bmr = (10 * weight) + (6.25 * height) - (5 * age);
        
        if (gender != null && gender.trim().equalsIgnoreCase("FEMALE")) {
            bmr -= 161;
        } else {
            bmr += 5;
        }
        
        return bmr;
    }
    
    /**
     * Get available nutrition plan types
     * Useful for UI dropdown menus
     * 
     * @return array of available plan types
     */
    public static String[] getPlanTypes() {
        return new String[]{"WEIGHT_LOSS", "MUSCLE_GAIN", "MAINTENANCE"};
    }
    
    /**
     * Get description of nutrition plan type
     * 
     * @param planType the plan type
     * @return description of the plan
     */
    public static String getPlanDescription(String planType) {
        if (planType == null) {
            return "Unknown plan type";
        }
        
        switch (planType.toUpperCase()) {
            case "WEIGHT_LOSS":
                return "Caloric deficit plan designed for fat loss while preserving muscle";
            case "MUSCLE_GAIN":
                return "Caloric surplus plan with high protein for muscle building";
            case "MAINTENANCE":
                return "Balanced nutrition plan for maintaining current weight and composition";
            default:
                return "Unknown plan type";
        }
    }
}