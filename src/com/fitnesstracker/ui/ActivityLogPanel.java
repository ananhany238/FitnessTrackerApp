package com.fitnesstracker.ui;



import com.fitnesstracker.singleton.ActivityTracker;
import com.fitnesstracker.factory.ExerciseFactory;
import com.fitnesstracker.model.Exercise;
import com.fitnesstracker.model.Activity;
import com.fitnesstracker.decorator.EquipmentDecorator;
import com.fitnesstracker.decorator.MusicDecorator;
import javax.swing.*;
import java.awt.*;


/**
 * ActivityLogPanel - Modern dark themed activity logging
 */

// Demonstrates Strategy Pattern: Different workout strategies can be swapped at runtime
// BeginnerWorkoutStrategy and AdvancedWorkoutStrategy implement the WorkoutStrategy interface
public class ActivityLogPanel extends JPanel {
    
    private JComboBox<String> exerciseTypeCombo;
    private JTextField exerciseNameField;
    private JSpinner durationSpinner;
    private JSpinner intensitySpinner;
    private JCheckBox equipmentCheck;
    private JTextField equipmentField;
    private JCheckBox musicCheck;
    private JTextField playlistField;
    private JTextArea notesArea;
    
    private ActivityTracker activityTracker;
    
    public ActivityLogPanel() {
        activityTracker = ActivityTracker.getInstance();
        setBackground(MainFrame.BG_MEDIUM);
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(MainFrame.BG_MEDIUM);
        
        JLabel titleLabel = new JLabel("Log New Activity");
        titleLabel.setFont(MainFrame.FONT_HEADER);
        titleLabel.setForeground(MainFrame.TEXT_PRIMARY);
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = createModernFormPanel();
        add(formPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonsPanel = createModernButtonsPanel();
        add(buttonsPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createModernFormPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(MainFrame.BG_MEDIUM);
        
        // Left column - Basic info
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(MainFrame.BG_DARK);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.ACCENT_BLUE, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        addSectionTitle(leftPanel, "Exercise Information");
        
        exerciseTypeCombo = createStyledComboBox(ExerciseFactory.getExerciseTypes());
        leftPanel.add(createFieldPanel("Exercise Type:", exerciseTypeCombo));
        leftPanel.add(Box.createVerticalStrut(15));
        
        exerciseNameField = createStyledTextField();
        leftPanel.add(createFieldPanel("Exercise Name:", exerciseNameField));
        leftPanel.add(Box.createVerticalStrut(15));
        
        durationSpinner = createStyledSpinner(new SpinnerNumberModel(30, 1, 300, 5));
        leftPanel.add(createFieldPanel("Duration (min):", durationSpinner));
        leftPanel.add(Box.createVerticalStrut(15));
        
        intensitySpinner = createStyledSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        leftPanel.add(createFieldPanel("Intensity (1-10):", intensitySpinner));
        
        // Right column - Optional features
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(MainFrame.BG_DARK);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.ACCENT_GREEN, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        addSectionTitle(rightPanel, "Optional Enhancements");
        
        // Equipment decorator
        equipmentCheck = createStyledCheckBox("Add Equipment");
        rightPanel.add(equipmentCheck);
        rightPanel.add(Box.createVerticalStrut(10));
        
        equipmentField = createStyledTextField();
        equipmentField.setEnabled(false);
        rightPanel.add(equipmentField);
        rightPanel.add(Box.createVerticalStrut(15));
        
        equipmentCheck.addActionListener(e -> 
            equipmentField.setEnabled(equipmentCheck.isSelected()));
        
        // Music decorator
        musicCheck = createStyledCheckBox("Add Music Playlist");
        rightPanel.add(musicCheck);
        rightPanel.add(Box.createVerticalStrut(10));
        
        playlistField = createStyledTextField();
        playlistField.setEnabled(false);
        rightPanel.add(playlistField);
        rightPanel.add(Box.createVerticalStrut(20));
        
        musicCheck.addActionListener(e -> 
            playlistField.setEnabled(musicCheck.isSelected()));
        
        // Notes section
        addSectionTitle(rightPanel, "Notes");
        notesArea = createStyledTextArea(4);
        JScrollPane notesScroll = new JScrollPane(notesArea);
        notesScroll.setBorder(BorderFactory.createLineBorder(MainFrame.BORDER_COLOR));
        rightPanel.add(notesScroll);
        
        // Add both columns
        JPanel columnsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        columnsPanel.setBackground(MainFrame.BG_MEDIUM);
        columnsPanel.add(leftPanel);
        columnsPanel.add(rightPanel);
        
        mainPanel.add(columnsPanel, BorderLayout.CENTER);
        
        return mainPanel;
    }
    
    private void addSectionTitle(JPanel panel, String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(MainFrame.ACCENT_GREEN);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(15));
    }
    
    private JPanel createFieldPanel(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(MainFrame.BG_DARK);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        
        JLabel label = new JLabel(labelText);
        label.setFont(MainFrame.FONT_NORMAL);
        label.setForeground(MainFrame.TEXT_SECONDARY);
        panel.add(label, BorderLayout.NORTH);
        
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panel.add(component, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setFont(MainFrame.FONT_NORMAL);
        combo.setBackground(MainFrame.BG_LIGHT);
        combo.setForeground(MainFrame.TEXT_PRIMARY);
        return combo;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(MainFrame.FONT_NORMAL);
        field.setBackground(MainFrame.BG_LIGHT);
        field.setForeground(MainFrame.TEXT_PRIMARY);
        field.setCaretColor(MainFrame.ACCENT_GREEN);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }
    
    private JSpinner createStyledSpinner(SpinnerModel model) {
        JSpinner spinner = new JSpinner(model);
        spinner.setFont(MainFrame.FONT_NORMAL);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setBackground(MainFrame.BG_LIGHT);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setForeground(MainFrame.TEXT_PRIMARY);
        return spinner;
    }
    
    private JCheckBox createStyledCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setFont(MainFrame.FONT_NORMAL);
        checkBox.setForeground(MainFrame.TEXT_PRIMARY);
        checkBox.setBackground(MainFrame.BG_DARK);
        checkBox.setFocusPainted(false);
        checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        return checkBox;
    }
    
    private JTextArea createStyledTextArea(int rows) {
        JTextArea area = new JTextArea(rows, 20);
        area.setFont(MainFrame.FONT_NORMAL);
        area.setBackground(MainFrame.BG_LIGHT);
        area.setForeground(MainFrame.TEXT_PRIMARY);
        area.setCaretColor(MainFrame.ACCENT_GREEN);
        area.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }
    
    private JPanel createModernButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panel.setBackground(MainFrame.BG_MEDIUM);
        
        JButton logButton = MainFrame.createStyledButton(" Log Activity", MainFrame.BUTTON_GREEN);
        logButton.setPreferredSize(new Dimension(180, 45));
        logButton.addActionListener(e -> logActivity());
        
        JButton clearButton = MainFrame.createStyledButton(" Clear Form", MainFrame.BUTTON_RED);
        clearButton.setPreferredSize(new Dimension(180, 45));
        clearButton.addActionListener(e -> clearForm());
        
        panel.add(logButton);
        panel.add(clearButton);
        
        return panel;
    }
    
    private void logActivity() {
        try {
            if (exerciseNameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter exercise name", 
                    "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String type = (String) exerciseTypeCombo.getSelectedItem();
            String name = exerciseNameField.getText().trim();
            int duration = (Integer) durationSpinner.getValue();
            int intensity = (Integer) intensitySpinner.getValue();
            
            Exercise exercise = ExerciseFactory.createExercise(type, name, duration, intensity);
            
            if (equipmentCheck.isSelected() && !equipmentField.getText().trim().isEmpty()) {
                exercise = new EquipmentDecorator(exercise, equipmentField.getText().trim());
            }
            
            if (musicCheck.isSelected() && !playlistField.getText().trim().isEmpty()) {
                exercise = new MusicDecorator(exercise, playlistField.getText().trim());
            }
            
            Activity activity = new Activity(exercise, notesArea.getText().trim());
            activityTracker.addActivity(activity);
            
            JOptionPane.showMessageDialog(this, 
                String.format(" Activity logged successfully!\n\n%s\n\nCalories burned: %.0f kcal", 
                    exercise.getDescription(), exercise.calculateCalories()),
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            clearForm();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearForm() {
        exerciseTypeCombo.setSelectedIndex(0);
        exerciseNameField.setText("");
        durationSpinner.setValue(30);
        intensitySpinner.setValue(5);
        equipmentCheck.setSelected(false);
        equipmentField.setText("");
        equipmentField.setEnabled(false);
        musicCheck.setSelected(false);
        playlistField.setText("");
        playlistField.setEnabled(false);
        notesArea.setText("");
    }
}