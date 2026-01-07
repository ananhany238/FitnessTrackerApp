package com.fitnesstracker.model;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Activity - Represents a completed exercise activity
 * 
 * Stores information about a workout session
 * Used by ActivityTracker Singleton
 * @version 1.0
 */
public class Activity {
    
    private Exercise exercise;
    private Date date;
    private double caloriesBurned;
    private String notes;
    
    /**
     * Constructor for Activity
     * 
     * @param exercise the exercise performed
     */
    public Activity(Exercise exercise) {
        this.exercise = exercise;
        this.date = new Date();
        this.caloriesBurned = exercise.calculateCalories();
        this.notes = "";
    }
    
    /**
     * Constructor with notes
     * 
     * @param exercise the exercise performed
     * @param notes additional notes about the activity
     */
    public Activity(Exercise exercise, String notes) {
        this(exercise);
        this.notes = notes;
    }
    
    /**
     * Get formatted date string
     * FIXED: Changed from sdf.getDate() to sdf.format(date)
     * 
     * @return formatted date
     */
    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);  // FIXED: This was the bug!
    }
    
    /**
     * Get activity summary
     * 
     * @return summary string
     */
    public String getSummary() {
        return String.format("%s - %s (%.0f cal)", 
            getFormattedDate(), exercise.getName(), caloriesBurned);
    }
    
    // Getters and Setters
    
    public Exercise getExercise() {
        return exercise;
    }
    
    public String getExerciseType() {
        return exercise.getExerciseType();
    }
    
    public int getDuration() {
        return exercise.getDuration();
    }
    
    public Date getDate() {
        return date;
    }
    
    public double getCaloriesBurned() {
        return caloriesBurned;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return getSummary();
    }
}