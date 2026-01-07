package com.fitnesstracker.observer;

/**
 * ActivityObserver - Observer Pattern Interface
 * 
 * Purpose: Allows components to be notified of activity changes
 * 
 * Pattern: Observer Pattern
 * 
 * Justification:
 * - Decouples activity tracking from UI updates
 * - Multiple UI components can react to activity changes independently
 * - Supports real-time updates across the application
 * - Enables flexible addition of new observers without modifying existing code
 * - Promotes loose coupling and high cohesion
 * 
 * Use Case:
 * - Update activity list when new activities are added
 * - Refresh statistics panels automatically
 * - Update goal progress based on new activities
 * @version 1.0
 */
public interface ActivityObserver {
    
    /**
     * Called when activities are updated in the ActivityTracker
     * Implementing classes should define how they respond to activity changes
     */
    void onActivityUpdated();
}