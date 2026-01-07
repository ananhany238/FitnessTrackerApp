package com.fitnesstracker.singleton;

import com.fitnesstracker.model.Activity;
import com.fitnesstracker.observer.ActivityObserver;
import com.fitnesstracker.database.ActivityDAO;
import java.util.ArrayList;
import java.util.List;

public class ActivityTracker {
    
    private static ActivityTracker instance;
    private List<Activity> activities;
    private List<ActivityObserver> observers;
    private ActivityDAO activityDAO; // DAO for database operations
    
    private ActivityTracker() {
        this.activities = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.activityDAO = new ActivityDAO(); //Initialize DAO
        
        // Load activities from database on startup
        loadActivitiesFromDatabase();
    }
    
    public static synchronized ActivityTracker getInstance() {
        if (instance == null) {
            instance = new ActivityTracker();
        }
        return instance;
    }
    
    // Load activities from database
    private void loadActivitiesFromDatabase() {
        try {
            activities = activityDAO.getAllActivities();
            System.out.println(" Loaded " + activities.size() + " activities from database");
        } catch (Exception e) {
            System.err.println(" Error loading activities: " + e.getMessage());
            activities = new ArrayList<>(); // Fallback to empty list
        }
    }
    
    public void addActivity(Activity activity) {
        // Save to database first
        int id = activityDAO.saveActivity(activity);
        
        if (id > 0) {
            // Only add to memory if database save was successful
            activities.add(activity);
            notifyObservers();
        } else {
            System.err.println("Failed to save activity to database");
        }
    }
    
    public List<Activity> getAllActivities() {
        return new ArrayList<>(activities);
    }
    
    public List<Activity> getActivitiesByType(String type) {
        List<Activity> filtered = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getExerciseType().equalsIgnoreCase(type)) {
                filtered.add(activity);
            }
        }
        return filtered;
    }
    
    public double getTotalCaloriesBurned() {
        //  Get from database for accuracy
        return activityDAO.getTotalCalories();
    }
    
    public int getTotalDuration() {
        // Get from database for accuracy
        return activityDAO.getTotalDuration();
    }
    
    public void clearActivities() {
        // Clear from database
        if (activityDAO.clearAllActivities()) {
            activities.clear();
            notifyObservers();
        }
    }
    
    // Observer pattern methods 
    public void addObserver(ActivityObserver observer) {
        observers.add(observer);
    }
    
    public void removeObserver(ActivityObserver observer) {
        observers.remove(observer);
    }
    
    private void notifyObservers() {
        for (ActivityObserver observer : observers) {
            observer.onActivityUpdated();
        }
    }
}