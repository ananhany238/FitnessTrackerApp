package com.fitnesstracker.ui;

import com.fitnesstracker.strategy.WorkoutStrategy;
import com.fitnesstracker.strategy.BeginnerWorkoutStrategy;
import com.fitnesstracker.strategy.AdvancedWorkoutStrategy;
import com.fitnesstracker.model.Exercise;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class WorkoutGeneratorPanel extends JPanel {
    
    private JComboBox<String> strategyCombo;
    private JComboBox<String> focusCombo;
    private JSpinner durationSpinner;
    private JTextArea descriptionArea;
    private JTable workoutTable;
    private DefaultTableModel tableModel;
    
    private WorkoutStrategy currentStrategy;
    
    public WorkoutGeneratorPanel() {
        currentStrategy = new BeginnerWorkoutStrategy();
        setBackground(MainFrame.BG_MEDIUM);
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(MainFrame.BG_MEDIUM);
        
        JLabel titleLabel = new JLabel(" Workout Plan Generator");
        titleLabel.setFont(MainFrame.FONT_HEADER);
        titleLabel.setForeground(MainFrame.TEXT_PRIMARY);
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        
        // Input panel
        JPanel inputPanel = createModernInputPanel();
        add(inputPanel, BorderLayout.WEST);
        
        // Results panel
        JPanel resultsPanel = createModernResultsPanel();
        add(resultsPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonsPanel = createModernButtonsPanel();
        add(buttonsPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createModernInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(MainFrame.BG_DARK);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.ACCENT_BLUE, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setPreferredSize(new Dimension(350, 0));
        
        addSectionTitle(panel, "Workout Parameters");
        
        strategyCombo = new JComboBox<>(new String[]{"Beginner Friendly", "Advanced Performance"});
        styleCombo(strategyCombo);
        strategyCombo.addActionListener(e -> updateStrategy());
        panel.add(createInputField("Training Level:", strategyCombo));
        panel.add(Box.createVerticalStrut(15));
        
        focusCombo = new JComboBox<>(new String[]{"CARDIO", "STRENGTH", "MIXED"});
        styleCombo(focusCombo);
        panel.add(createInputField("Workout Focus:", focusCombo));
        panel.add(Box.createVerticalStrut(15));
        
        durationSpinner = new JSpinner(new SpinnerNumberModel(45, 15, 180, 5));
        styleSpinner(durationSpinner);
        panel.add(createInputField("Duration (min):", durationSpinner));
        panel.add(Box.createVerticalStrut(20));
        
        addSectionTitle(panel, "Strategy Description");
        
        descriptionArea = new JTextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setFont(MainFrame.FONT_SMALL);
        descriptionArea.setBackground(MainFrame.BG_LIGHT);
        descriptionArea.setForeground(MainFrame.TEXT_SECONDARY);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        descriptionArea.setText(currentStrategy.getDescription());
        
        JScrollPane descScroll = new JScrollPane(descriptionArea);
        descScroll.setBorder(BorderFactory.createLineBorder(MainFrame.BORDER_COLOR));
        descScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        descScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(descScroll);
        
        return panel;
    }
    
    private void addSectionTitle(JPanel panel, String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(MainFrame.ACCENT_BLUE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
    }
    
    private JPanel createInputField(String label, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(MainFrame.BG_DARK);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        
        JLabel l = new JLabel(label);
        l.setFont(MainFrame.FONT_NORMAL);
        l.setForeground(MainFrame.TEXT_SECONDARY);
        panel.add(l, BorderLayout.NORTH);
        
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panel.add(component, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void styleCombo(JComboBox<?> combo) {
        combo.setFont(MainFrame.FONT_NORMAL);
        combo.setBackground(MainFrame.BG_LIGHT);
        combo.setForeground(MainFrame.TEXT_PRIMARY);
    }
    
    private void styleSpinner(JSpinner spinner) {
        spinner.setFont(MainFrame.FONT_NORMAL);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setBackground(MainFrame.BG_LIGHT);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setForeground(MainFrame.TEXT_PRIMARY);
    }
    
    private JPanel createModernResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(MainFrame.BG_DARK);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.ACCENT_GREEN, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(" Generated Workout Plan");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(MainFrame.ACCENT_GREEN);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        String[] columns = {"Exercise", "Type", "Duration", "Intensity", "Est. Calories"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        workoutTable = new JTable(tableModel);
        workoutTable.setFont(MainFrame.FONT_NORMAL);
        workoutTable.setRowHeight(32);
        workoutTable.setBackground(MainFrame.BG_DARK);
        workoutTable.setForeground(MainFrame.TEXT_PRIMARY);
        workoutTable.setGridColor(MainFrame.BORDER_COLOR);
        workoutTable.setSelectionBackground(MainFrame.ACCENT_GREEN);
        workoutTable.setSelectionForeground(Color.BLACK);
        
        workoutTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        workoutTable.getTableHeader().setBackground(MainFrame.BG_MEDIUM);
        workoutTable.getTableHeader().setForeground(MainFrame.ACCENT_GREEN);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(MainFrame.BG_DARK);
        centerRenderer.setForeground(MainFrame.TEXT_PRIMARY);
        for (int i = 0; i < workoutTable.getColumnCount(); i++) {
            workoutTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(workoutTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(MainFrame.BORDER_COLOR));
        scrollPane.getViewport().setBackground(MainFrame.BG_DARK);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createModernButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(MainFrame.BG_MEDIUM);
        
        JButton generateButton = MainFrame.createStyledButton(" Generate Workout", MainFrame.BUTTON_GREEN);
        generateButton.setPreferredSize(new Dimension(200, 45));
        generateButton.addActionListener(e -> generateWorkout());
        
        JButton clearButton = MainFrame.createStyledButton("Clear", MainFrame.BUTTON_RED);
        clearButton.setPreferredSize(new Dimension(150, 45));
        clearButton.addActionListener(e -> tableModel.setRowCount(0));
        
        panel.add(generateButton);
        panel.add(clearButton);
        
        return panel;
    }
    
    private void updateStrategy() {
        String selected = (String) strategyCombo.getSelectedItem();
        
        if (selected.equals("Beginner Friendly")) {
            currentStrategy = new BeginnerWorkoutStrategy();
        } else {
            currentStrategy = new AdvancedWorkoutStrategy();
        }
        
        descriptionArea.setText(currentStrategy.getDescription());
    }
    
    private void generateWorkout() {
        try {
            tableModel.setRowCount(0);
            
            int duration = (Integer) durationSpinner.getValue();
            String focus = (String) focusCombo.getSelectedItem();
            
            List<Exercise> workout = currentStrategy.generateWorkout(duration, focus);
            
            double totalCalories = 0;
            for (Exercise exercise : workout) {
                Object[] row = {
                    exercise.getName(),
                    exercise.getExerciseType(),
                    exercise.getDuration() + " min",
                    exercise.getIntensity() + "/10",
                    String.format("%.0f kcal", exercise.calculateCalories())
                };
                tableModel.addRow(row);
                totalCalories += exercise.calculateCalories();
            }
            
            JOptionPane.showMessageDialog(this,
                String.format(" Workout generated using %s strategy!\n\n" +
                    " Total Exercises: %d\n" +
                    " Total Duration: %d minutes\n" +
                    " Estimated Calories: %.0f kcal",
                    currentStrategy.getStrategyName(),
                    workout.size(),
                    duration,
                    totalCalories),
                "Workout Generated",
                JOptionPane.INFORMATION_MESSAGE);
           
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}