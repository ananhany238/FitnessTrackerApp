package com.fitnesstracker.ui;

import com.fitnesstracker.singleton.ActivityTracker;
import com.fitnesstracker.singleton.GoalManagementSystem;
import com.fitnesstracker.observer.ActivityObserver;
import com.fitnesstracker.observer.GoalObserver;
import javax.swing.*;
import java.awt.*;

/**
 * DashboardPanel - Modern dark theme dashboard
 */
public class DashboardPanel extends JPanel implements ActivityObserver, GoalObserver {
    
    private JLabel totalActivitiesLabel;
    private JLabel totalCaloriesLabel;
    private JLabel totalDurationLabel;
    private JLabel activeGoalsLabel;
    private JTextArea recentActivitiesArea;
    
    private ActivityTracker activityTracker;
    private GoalManagementSystem goalSystem;
    
    public DashboardPanel() {
        activityTracker = ActivityTracker.getInstance();
        goalSystem = GoalManagementSystem.getInstance();
        
        activityTracker.addObserver(this);
        goalSystem.addObserver(this);
        
        setBackground(MainFrame.BG_MEDIUM);
        initializeComponents();
        updateStatistics();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Modern title panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(MainFrame.BG_MEDIUM);
        
        JLabel titleLabel = new JLabel(" Dashboard Overview");
        titleLabel.setFont(MainFrame.FONT_HEADER);
        titleLabel.setForeground(MainFrame.TEXT_PRIMARY);
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        
        // Statistics panel with modern cards
        JPanel statsPanel = createModernStatisticsPanel();
        add(statsPanel, BorderLayout.CENTER);
        
        // Recent activities panel
        JPanel recentPanel = createModernRecentActivitiesPanel();
        add(recentPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createModernStatisticsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBackground(MainFrame.BG_MEDIUM);
        
        // Total Activities Card - Blue
        JPanel activitiesCard = createModernStatCard(
            "Total Activities", "0", "🏃", MainFrame.ACCENT_BLUE);
        totalActivitiesLabel = (JLabel) activitiesCard.getComponent(2);
        statsPanel.add(activitiesCard);
        
        // Total Calories Card - Green
        JPanel caloriesCard = createModernStatCard(
            "Calories Burned", "0 kcal", "🔥", MainFrame.BUTTON_GREEN);
        totalCaloriesLabel = (JLabel) caloriesCard.getComponent(2);
        statsPanel.add(caloriesCard);
        
        // Total Duration Card - Blue
        JPanel durationCard = createModernStatCard(
            "Total Duration", "0 min", "⏱️", MainFrame.ACCENT_BLUE);
        totalDurationLabel = (JLabel) durationCard.getComponent(2);
        statsPanel.add(durationCard);
        
        // Active Goals Card - Green
        JPanel goalsCard = createModernStatCard(
            "Active Goals", "0", "🎯", MainFrame.ACCENT_GREEN);
        activeGoalsLabel = (JLabel) goalsCard.getComponent(2);
        statsPanel.add(goalsCard);
        
        return statsPanel;
    }
    
    /**
     * Create modern stat card with gradient and shadow effect
     */
    private JPanel createModernStatCard(String title, String value, String icon, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background with rounded corners
                g2d.setColor(MainFrame.BG_DARK);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Accent border
                g2d.setColor(accentColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                
                // Accent gradient on left side
                GradientPaint gradient = new GradientPaint(
                    0, 0, accentColor,
                    0, getHeight(), new Color(accentColor.getRed(), accentColor.getGreen(), accentColor.getBlue(), 50)
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, 5, getHeight(), 15, 15);
            }
        };
        
        card.setLayout(new BorderLayout(15, 10));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(iconLabel, BorderLayout.WEST);
        
        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(MainFrame.FONT_NORMAL);
        titleLabel.setForeground(MainFrame.TEXT_SECONDARY);
        card.add(titleLabel, BorderLayout.NORTH);
        
        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        valueLabel.setForeground(accentColor);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createModernRecentActivitiesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(MainFrame.BG_MEDIUM);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.setPreferredSize(new Dimension(0, 180));
        
        JLabel titleLabel = new JLabel(" Recent Activities");
        titleLabel.setFont(MainFrame.FONT_HEADER);
        titleLabel.setForeground(MainFrame.TEXT_PRIMARY);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        recentActivitiesArea = new JTextArea();
        recentActivitiesArea.setEditable(false);
        recentActivitiesArea.setFont(MainFrame.FONT_NORMAL);
        recentActivitiesArea.setBackground(MainFrame.BG_DARK);
        recentActivitiesArea.setForeground(MainFrame.TEXT_PRIMARY);
        recentActivitiesArea.setCaretColor(MainFrame.ACCENT_GREEN);
        recentActivitiesArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(recentActivitiesArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(MainFrame.BORDER_COLOR));
        scrollPane.getViewport().setBackground(MainFrame.BG_DARK);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void updateStatistics() {
        int totalActivities = activityTracker.getAllActivities().size();
        totalActivitiesLabel.setText(String.valueOf(totalActivities));
        
        double totalCalories = activityTracker.getTotalCaloriesBurned();
        totalCaloriesLabel.setText(String.format("%.0f kcal", totalCalories));
        
        int totalDuration = activityTracker.getTotalDuration();
        totalDurationLabel.setText(totalDuration + " min");
        
        int activeGoals = goalSystem.getActiveGoals().size();
        activeGoalsLabel.setText(String.valueOf(activeGoals));
        
        updateRecentActivities();
    }
    
    private void updateRecentActivities() {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = activityTracker.getAllActivities().size() - 1; i >= 0 && count < 5; i--) {
            sb.append("• ").append(activityTracker.getAllActivities().get(i).getSummary()).append("\n");
            count++;
        }
        
        if (sb.length() == 0) {
            sb.append("No activities logged yet.\n");
            sb.append("Start by logging your first activity in the 'Log Activity' tab!");
        }
        
        recentActivitiesArea.setText(sb.toString());
    }
    
    @Override
    public void onActivityUpdated() {
        updateStatistics();
    }
    
    @Override
    public void onGoalUpdated() {
        updateStatistics();
    }
}