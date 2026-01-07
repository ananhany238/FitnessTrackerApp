package com.fitnesstracker.ui;

import com.fitnesstracker.database.GoalDAO;
import com.fitnesstracker.singleton.GoalManagementSystem;
import com.fitnesstracker.model.FitnessGoal;
import com.fitnesstracker.observer.GoalObserver;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * GoalsPanel - Manage fitness goals 
 * 
 * Demonstrates Singleton: GoalManagementSystem ensures single instance of goal manager
 * Demonstrates Observer Pattern: GoalsPanel observes goal updates via GoalObserver interface
 */
public class GoalsPanel extends JPanel implements GoalObserver {
    
    private JTable goalsTable;
    private DefaultTableModel tableModel;
    private GoalManagementSystem goalSystem;
    
    public GoalsPanel() {
        goalSystem = GoalManagementSystem.getInstance();
        goalSystem.addObserver(this);
        
        setBackground(MainFrame.BG_MEDIUM);
        initializeComponents();
        loadGoals();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(MainFrame.BG_MEDIUM);
        
        JLabel titleLabel = new JLabel("Fitness Goals");
        titleLabel.setFont(MainFrame.FONT_HEADER);
        titleLabel.setForeground(MainFrame.TEXT_PRIMARY);
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        
        // Table panel
        JPanel tablePanel = createModernTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonsPanel = createModernButtonsPanel();
        add(buttonsPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createModernTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MainFrame.BG_DARK);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.ACCENT_BLUE, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        String[] columns = {"Goal Name", "Type", "Progress", "Target", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        goalsTable = new JTable(tableModel);
        goalsTable.setFont(MainFrame.FONT_NORMAL);
        goalsTable.setRowHeight(35);
        goalsTable.setBackground(MainFrame.BG_DARK);
        goalsTable.setForeground(MainFrame.TEXT_PRIMARY);
        goalsTable.setGridColor(MainFrame.BORDER_COLOR);
        goalsTable.setSelectionBackground(MainFrame.ACCENT_BLUE);
        goalsTable.setSelectionForeground(Color.WHITE);
        goalsTable.setShowVerticalLines(true);
        goalsTable.setShowHorizontalLines(true);
        
        // Style header
        goalsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        goalsTable.getTableHeader().setBackground(MainFrame.BG_MEDIUM);
        goalsTable.getTableHeader().setForeground(MainFrame.ACCENT_GREEN);
        goalsTable.getTableHeader().setBorder(BorderFactory.createLineBorder(MainFrame.BORDER_COLOR));
        
        // Center align cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setBackground(MainFrame.BG_DARK);
        centerRenderer.setForeground(MainFrame.TEXT_PRIMARY);
        for (int i = 0; i < goalsTable.getColumnCount(); i++) {
            goalsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        JScrollPane scrollPane = new JScrollPane(goalsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(MainFrame.BORDER_COLOR));
        scrollPane.getViewport().setBackground(MainFrame.BG_DARK);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createModernButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(MainFrame.BG_MEDIUM);
        
        JButton addButton = MainFrame.createStyledButton("Add Goal", MainFrame.BUTTON_GREEN);
        addButton.addActionListener(e -> showAddGoalDialog());
        
        JButton updateButton = MainFrame.createStyledButton("Update Progress", MainFrame.BUTTON_BLUE);
        updateButton.addActionListener(e -> showUpdateProgressDialog());
        
        JButton deleteButton = MainFrame.createStyledButton("Delete Goal", MainFrame.BUTTON_RED);
        deleteButton.addActionListener(e -> deleteSelectedGoal());
        
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        
        return panel;
    }
    
   private void loadGoals() {
    // Ensure table is fully cleared
    tableModel.setRowCount(0);

    for (FitnessGoal goal : goalSystem.getAllGoals()) {
        Object[] row = {
            goal.getName(),
            goal.getGoalType(),
            String.format("%.1f %s", goal.getCurrentProgress(), goal.getUnit()),
            String.format("%.1f %s", goal.getTargetValue(), goal.getUnit()),
            goal.isCompleted()
                ? "Completed"
                : String.format("%.0f%%", goal.getProgressPercentage())
        };
        tableModel.addRow(row);
    }
}

    
    
    private void showAddGoalDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), 
            "Add New Goal", true);
        dialog.setLayout(new BorderLayout(15, 15));
        dialog.setSize(550, 450); 
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(MainFrame.BG_MEDIUM);
        
        // Main form panel with BoxLayout 
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(MainFrame.BG_DARK);
        
        // Goal Name 
        JPanel namePanel = new JPanel(new BorderLayout(10, 5));
        namePanel.setBackground(MainFrame.BG_DARK);
        namePanel.setMaximumSize(new Dimension(500, 70));
        
        JLabel nameLabel = new JLabel("Goal Name:");
        nameLabel.setFont(MainFrame.FONT_NORMAL);
        nameLabel.setForeground(MainFrame.TEXT_PRIMARY);
        namePanel.add(nameLabel, BorderLayout.NORTH);
        
        JTextField nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16)); 
        nameField.setPreferredSize(new Dimension(480, 40)); 
        nameField.setBackground(MainFrame.BG_LIGHT);
        nameField.setForeground(MainFrame.TEXT_PRIMARY);
        nameField.setCaretColor(MainFrame.ACCENT_GREEN);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        namePanel.add(nameField, BorderLayout.CENTER);
        formPanel.add(namePanel);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Goal Type
        JPanel typePanel = createFieldRow("Goal Type:");
        String[] goalTypes = {"WEIGHT_LOSS", "MUSCLE_GAIN", "ENDURANCE", "STRENGTH"};
        JComboBox<String> typeCombo = new JComboBox<>(goalTypes);
        styleDialogCombo(typeCombo);
        typePanel.add(typeCombo, BorderLayout.CENTER);
        formPanel.add(typePanel);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Target Value and Unit 
        JPanel targetPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        targetPanel.setBackground(MainFrame.BG_DARK);
        targetPanel.setMaximumSize(new Dimension(500, 60));
        
        JLabel targetLabel = new JLabel("Target:");
        targetLabel.setFont(MainFrame.FONT_NORMAL);
        targetLabel.setForeground(MainFrame.TEXT_PRIMARY);
        targetPanel.add(targetLabel);
        
        JSpinner targetSpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.1, 1000.0, 0.5));
        targetSpinner.setPreferredSize(new Dimension(120, 35));
        styleDialogSpinner(targetSpinner);
        targetPanel.add(targetSpinner);
        
        JLabel unitLabel = new JLabel("Unit:");
        unitLabel.setFont(MainFrame.FONT_NORMAL);
        unitLabel.setForeground(MainFrame.TEXT_PRIMARY);
        targetPanel.add(unitLabel);
        
        String[] units = {"kg", "calories", "minutes", "reps"};
        JComboBox<String> unitCombo = new JComboBox<>(units);
        unitCombo.setPreferredSize(new Dimension(120, 35));
        styleDialogCombo(unitCombo);
        targetPanel.add(unitCombo);
        
        formPanel.add(targetPanel);
        formPanel.add(Box.createVerticalStrut(10));
        
        // Description - MUCH BIGGER with JTextArea
        JPanel descPanel = new JPanel(new BorderLayout(10, 5));
        descPanel.setBackground(MainFrame.BG_DARK);
        descPanel.setMaximumSize(new Dimension(500, 120));
        
        JLabel descLabel = new JLabel("Description (optional):");
        descLabel.setFont(MainFrame.FONT_NORMAL);
        descLabel.setForeground(MainFrame.TEXT_PRIMARY);
        descPanel.add(descLabel, BorderLayout.NORTH);
        
        JTextArea descArea = new JTextArea(3, 40); // 3 rows
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descArea.setBackground(MainFrame.BG_LIGHT);
        descArea.setForeground(MainFrame.TEXT_PRIMARY);
        descArea.setCaretColor(MainFrame.ACCENT_GREEN);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.BORDER_COLOR, 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBorder(BorderFactory.createLineBorder(MainFrame.BORDER_COLOR, 2));
        descScroll.setPreferredSize(new Dimension(480, 90));
        descPanel.add(descScroll, BorderLayout.CENTER);
        
        formPanel.add(descPanel);
        
        dialog.add(formPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBackground(MainFrame.BG_MEDIUM);
        
        JButton saveButton = MainFrame.createStyledButton("Save Goal", MainFrame.BUTTON_GREEN);
        saveButton.setPreferredSize(new Dimension(150, 45));
        saveButton.addActionListener(e -> {
            if (nameField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, 
                    "Please enter a goal name", 
                    "Validation Error", 
                    JOptionPane.WARNING_MESSAGE);
                nameField.requestFocus();
                return;
            }
            
            FitnessGoal goal = new FitnessGoal(
                nameField.getText().trim(),
                descArea.getText().trim(),
                (String) typeCombo.getSelectedItem(),
                (Double) targetSpinner.getValue(),
                (String) unitCombo.getSelectedItem()
            );
            
            goalSystem.addGoal(goal);
            JOptionPane.showMessageDialog(dialog, 
                "Goal added successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        JButton cancelButton = MainFrame.createStyledButton("Cancel", MainFrame.BUTTON_RED);
        cancelButton.setPreferredSize(new Dimension(150, 45));
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        // Set focus to name field
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                nameField.requestFocus();
            }
        });
        
        dialog.setVisible(true);
    }
    
    /**
     * Helper method to create a field row
     */
    private JPanel createFieldRow(String labelText) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(MainFrame.BG_DARK);
        panel.setMaximumSize(new Dimension(500, 60));
        
        JLabel label = new JLabel(labelText);
        label.setFont(MainFrame.FONT_NORMAL);
        label.setForeground(MainFrame.TEXT_PRIMARY);
        panel.add(label, BorderLayout.NORTH);
        
        return panel;
    }
    
    private void styleDialogCombo(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(MainFrame.BG_LIGHT);
        combo.setForeground(MainFrame.TEXT_PRIMARY);
        combo.setPreferredSize(new Dimension(200, 35));
    }
    
    private void styleDialogSpinner(JSpinner spinner) {
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setBackground(MainFrame.BG_LIGHT);
            textField.setForeground(MainFrame.TEXT_PRIMARY);
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }
    
    private void showUpdateProgressDialog() {
        int selectedRow = goalsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a goal to update", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        FitnessGoal goal = goalSystem.getAllGoals().get(selectedRow);
        
        String input = JOptionPane.showInputDialog(this, 
            String.format("Update Progress for: %s\n\n" +
                          "Current: %.1f %s\n" +
                          "Target: %.1f %s\n\n" +
                          "Enter new progress value:",
                goal.getName(), 
                goal.getCurrentProgress(), goal.getUnit(), 
                goal.getTargetValue(), goal.getUnit()),
            goal.getCurrentProgress());
        
        if (input != null && !input.trim().isEmpty()) {
            try {
                double newProgress = Double.parseDouble(input);
                if (newProgress < 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Progress cannot be negative", 
                        "Invalid Input", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                goalSystem.updateGoalProgress(goal, newProgress);
                
                String message = goal.isCompleted() 
                    ? "Congratulations! Goal completed!" 
                    : " Progress updated successfully!";
                
                JOptionPane.showMessageDialog(this, message, 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Please enter a valid number", 
                    "Invalid Format", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
  private void deleteSelectedGoal() {

    int viewRow = goalsTable.getSelectedRow();

    // No selection
    if (viewRow == -1) {
        JOptionPane.showMessageDialog(
            this,
            "Please select a goal to delete",
            "Warning",
            JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    int modelRow = goalsTable.convertRowIndexToModel(viewRow);
    FitnessGoal selectedGoal = goalSystem.getAllGoals().get(modelRow);

    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Delete goal: '" + selectedGoal.getName() + "'?\nThis action cannot be undone.",
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    goalSystem.removeGoal(selectedGoal);

    JOptionPane.showMessageDialog(
        this,
        "Goal deleted successfully!",
        "Success",
        JOptionPane.INFORMATION_MESSAGE
    );

    loadGoals(); // refresh table
}    
    @Override
    public void onGoalUpdated() {
        loadGoals();
    }
}