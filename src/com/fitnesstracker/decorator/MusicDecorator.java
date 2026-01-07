package com.fitnesstracker.decorator;

import com.fitnesstracker.model.Exercise;

/**
 * MusicDecorator - Concrete Decorator
 * 
 * Adds music/audio tracking to exercises
 * Part of Decorator Pattern implementation
 * @version 1.0
 */
public class MusicDecorator extends ExerciseDecorator {
    
    private String playlist;
    private int bpm; // beats per minute
    
    /**
     * Constructor for MusicDecorator
     * 
     * @param exercise the exercise to decorate
     * @param playlist the music playlist name
     */
    public MusicDecorator(Exercise exercise, String playlist) {
        super(exercise);
        this.playlist = playlist;
        this.bpm = 120; // default tempo
    }
    
    /**
     * Constructor with BPM
     * 
     * @param exercise the exercise to decorate
     * @param playlist the music playlist name
     * @param bpm beats per minute of the music
     */
    public MusicDecorator(Exercise exercise, String playlist, int bpm) {
        super(exercise);
        this.playlist = playlist;
        this.bpm = bpm;
    }
    
    /**
     * Enhanced description including music info
     * 
     * @return description with music details
     */
    @Override
    public String getDescription() {
        return decoratedExercise.getDescription() + 
               String.format(" | Music: %s (%d BPM)", playlist, bpm);
    }
    
    /**
     * Calculate calories with music motivation bonus
     * Music can improve workout intensity and duration
     * 
     * @return adjusted calories
     */
    @Override
    public double calculateCalories() {
        double baseCalories = decoratedExercise.calculateCalories();
        // 3% bonus for music motivation
        return baseCalories * 1.03;
    }
    
    public String getPlaylist() {
        return playlist;
    }
    
    public int getBpm() {
        return bpm;
    }
}