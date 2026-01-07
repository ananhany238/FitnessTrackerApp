package com.fitnesstracker.singleton;

import com.fitnesstracker.model.FitnessGoal;
import com.fitnesstracker.observer.GoalObserver;
import com.fitnesstracker.database.GoalDAO;
import java.util.ArrayList;
import java.util.List;

/**
 * GoalManagementSystem - Singleton with Database Integration
 * 
 */
public class GoalManagementSystem {
    
    private static GoalManagementSystem instance;
    private List<FitnessGoal> goals;
    private List<GoalObserver> observers;
    private GoalDAO goalDAO; // Database access
    
    /**
     * Private constructor - Singleton pattern
     */
    private GoalManagementSystem() {
        this.goals = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.goalDAO = new GoalDAO();
        
        // Load goals from database on startup
        loadGoalsFromDatabase();
    }
    
    /**
     * Get singleton instance
     */
    public static synchronized GoalManagementSystem getInstance() {
        if (instance == null) {
            instance = new GoalManagementSystem();
        }
        return instance;
    }
    
    /**
     * Load goals from database
     */
    private void loadGoalsFromDatabase() {
        try {
            goals = goalDAO.getAllGoals();
            System.out.println(" Loaded " + goals.size() + " goals from database");
        } catch (Exception e) {
            System.err.println(" Error loading goals: " + e.getMessage());
            goals = new ArrayList<>(); // Fallback to empty list
        }
    }
    
    /**
     * Add a new fitness goal
     * Saves to database first, then adds to memory
     * 
     * @param goal the fitness goal to add
     */
    public void addGoal(FitnessGoal goal) {
        // Save to database first
        int id = goalDAO.insertGoal(goal);
        
        if (id > 0) {
            // Only add to memory if database save was successful
            goals.add(goal);
            notifyObservers();
        } else {
            System.err.println("❌ Failed to save goal to database");
        }
    }
    
    /**
     * Remove a fitness goal
     * 
     * @param goal the goal to remove
     */
   public void removeGoal(FitnessGoal goal) {
    if (goal == null) return;

    boolean deleted = goalDAO.deleteGoal(goal.getId());

    if (deleted) {
        goals.remove(goal);
        notifyObservers();
    } else {
        System.err.println("Failed to delete goal from database");
    }
}

    
    /**
     * Get all fitness goals
     * 
     * @return list of all goals
     */
    public List<FitnessGoal> getAllGoals() {
        return new ArrayList<>(goals);
    }
    
    /**
     * Get active (incomplete) goals
     * 
     * @return list of active goals
     */
    public List<FitnessGoal> getActiveGoals() {
        List<FitnessGoal> activeGoals = new ArrayList<>();
        for (FitnessGoal goal : goals) {
            if (!goal.isCompleted()) {
                activeGoals.add(goal);
            }
        }
        return activeGoals;
    }
    
    /**
     * Get completed goals
     * 
     * @return list of completed goals
     */
    public List<FitnessGoal> getCompletedGoals() {
        List<FitnessGoal> completedGoals = new ArrayList<>();
        for (FitnessGoal goal : goals) {
            if (goal.isCompleted()) {
                completedGoals.add(goal);
            }
        }
        return completedGoals;
    }
    
    /**
     * Update progress for a specific goal
     * Updates database and notifies observers
     * 
     * @param goal the goal to update
     * @param currentProgress the new progress value
     */
   public void updateGoalProgress(FitnessGoal goal, double currentProgress) {
    goal.setCurrentProgress(currentProgress);

    if (goalDAO.updateGoalProgress(goal)) {
        notifyObservers();
    } else {
        System.err.println("Failed to update goal progress");
    }
}

   
    // Observer Pattern Methods
    
    /**
     * Register an observer to be notified of goal changes
     * 
     * @param observer the observer to register
     */
    public void addObserver(GoalObserver observer) {
        observers.add(observer);
    }
    
    /**
     * Remove an observer
     * 
     * @param observer the observer to remove
     */
    public void removeObserver(GoalObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * Notify all observers of goal changes
     */
    private void notifyObservers() {
        for (GoalObserver observer : observers) {
            observer.onGoalUpdated();
        }
    }
}

