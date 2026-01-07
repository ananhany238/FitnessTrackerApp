package com.fitnesstracker.database;

import com.fitnesstracker.model.Activity;
import com.fitnesstracker.model.Exercise;
import com.fitnesstracker.factory.ExerciseFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ActivityDAO - Data Access Object for Activity operations
 * 
 * Purpose: Handles all database operations for activities
 * 
 * CRUD Operations:
 * - Create: Insert new activity
 * - Read: Retrieve activities
 * - Update: Update activity notes
 * - Delete: Remove activity
 * @version 1.0
 */
public class ActivityDAO {
    
    private DatabaseManager dbManager;
    
    public ActivityDAO() {
        this.dbManager = DatabaseManager.getInstance();
    }
    
    /**
     * Save activity to database
     * 
     * @param activity the activity to save
     * @return the generated activity ID, or -1 if failed
     */
    public int saveActivity(Activity activity) {
        String sql = "INSERT INTO Activities " +
                    "(ExerciseName, ExerciseType, Duration, Intensity, " +
                    "CaloriesBurned, ActivityDate, Notes) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            Exercise exercise = activity.getExercise();
            
            pstmt.setString(1, exercise.getName());
            pstmt.setString(2, exercise.getExerciseType());
            pstmt.setInt(3, exercise.getDuration());
            pstmt.setInt(4, exercise.getIntensity());
            pstmt.setDouble(5, activity.getCaloriesBurned());
            pstmt.setTimestamp(6, new Timestamp(activity.getDate().getTime()));
            pstmt.setString(7, activity.getNotes());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Get generated ID
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println(" Activity saved with ID: " + id);
                    return id;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error saving activity: " + e.getMessage());
            e.printStackTrace();
        }
        
        return -1;
    }
    
    /**
     * Get all activities from database
     * 
     * @return list of all activities
     */
    public List<Activity> getAllActivities() {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT * FROM Activities ORDER BY ActivityDate DESC";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Activity activity = createActivityFromResultSet(rs);
                if (activity != null) {
                    activities.add(activity);
                }
            }
            
            System.out.println(" Retrieved " + activities.size() + " activities from database");
            
        } catch (SQLException e) {
            System.err.println("Error retrieving activities: " + e.getMessage());
            e.printStackTrace();
        }
        
        return activities;
    }
    
    /**
     * Get activities by type
     * 
     * @param type exercise type (CARDIO, STRENGTH, FLEXIBILITY)
     * @return list of activities matching the type
     */
    public List<Activity> getActivitiesByType(String type) {
        List<Activity> activities = new ArrayList<>();
        String sql = "SELECT * FROM Activities WHERE ExerciseType = ? ORDER BY ActivityDate DESC";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, type);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Activity activity = createActivityFromResultSet(rs);
                if (activity != null) {
                    activities.add(activity);
                }
            }
            
            rs.close();
            
        } catch (SQLException e) {
            System.err.println("Error retrieving activities by type: " + e.getMessage());
            e.printStackTrace();
        }
        
        return activities;
    }
    
    /**
     * Get total calories burned
     * 
     * @return total calories across all activities
     */
    public double getTotalCalories() {
        String sql = "SELECT SUM(CaloriesBurned) as Total FROM Activities";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getDouble("Total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error calculating total calories: " + e.getMessage());
        }
        
        return 0.0;
    }
    
    /**
     * Get total duration of all activities
     * 
     * @return total duration in minutes
     */
    public int getTotalDuration() {
        String sql = "SELECT SUM(Duration) as Total FROM Activities";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("Total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error calculating total duration: " + e.getMessage());
        }
        
        return 0;
    }
    
    /**
     * Delete activity by ID
     * 
     * @param activityId the ID of activity to delete
     * @return true if deleted successfully
     */
    public boolean deleteActivity(int activityId) {
        String sql = "DELETE FROM Activities WHERE ActivityID = ?";
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, activityId);
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println(" Activity deleted: ID " + activityId);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Error deleting activity: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Clear all activities from database
     * 
     * @return true if cleared successfully
     */
    public boolean clearAllActivities() {
        String sql = "DELETE FROM Activities";
        
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {
            
            int rowsDeleted = stmt.executeUpdate(sql);
            System.out.println(" Cleared " + rowsDeleted + " activities from database");
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error clearing activities: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Helper method to create Activity object from ResultSet
     * 
     * @param rs the ResultSet containing activity data
     * @return Activity object or null if error
     */
    private Activity createActivityFromResultSet(ResultSet rs) {
        try {
            String name = rs.getString("ExerciseName");
            String type = rs.getString("ExerciseType");
            int duration = rs.getInt("Duration");
            int intensity = rs.getInt("Intensity");
            String notes = rs.getString("Notes");
            
            // Recreate exercise using Factory
            Exercise exercise = ExerciseFactory.createExercise(type, name, duration, intensity);
            
            // Create activity
            Activity activity = new Activity(exercise, notes);
            
            return activity;
            
        } catch (SQLException e) {
            System.err.println("Error creating activity from result set: " + e.getMessage());
            return null;
        }
    }
}