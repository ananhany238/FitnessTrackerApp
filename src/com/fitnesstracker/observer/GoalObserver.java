package com.fitnesstracker.observer;

/**
 * GoalObserver - Observer Pattern Interface
 * 
 * Purpose: Allows components to be notified of goal changes
 * 
 * Pattern: Observer Pattern
 * 
 * Justification:
 * - Enables automatic UI updates when goals change
 * - Multiple components can track goal progress independently
 * - Supports notifications and alerts for goal completion
 * - Decouples goal management from presentation layer
 * 
 * Use Case:
 * - Update goal list display
 * - Show progress bars
 * - Trigger completion notifications
 * - Update dashboard statistics
 * 
 * @author Fitness Tracker Team
 * @version 1.0
 */
public interface GoalObserver {
    
    /**
     * Called when goals are updated in the GoalManagementSystem
     * Implementing classes should define how they respond to goal changes
     */
    void onGoalUpdated();
}