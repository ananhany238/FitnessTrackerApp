package com.fitnesstracker;

import com.fitnesstracker.ui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main class - Entry point for Fitness Tracker Application
 * This application demonstrates the use of multiple design patterns:
 * - Singleton Pattern (Mandatory)
 * - Factory Pattern (Mandatory)
 * - Observer Pattern (For tracking updates)
 * - Strategy Pattern (For workout strategies)
 * - Decorator Pattern (For enhanced exercises)
 * @version 1.0
 */
public class Main {
    
    /**
     * Main method to launch the application
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Set system look and feel for better UI appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Launch GUI on Event Dispatch Thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
            }
        });
    }
}