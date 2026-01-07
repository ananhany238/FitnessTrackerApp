package com.fitnesstracker.ui;

import com.fitnesstracker.factory.NutritionPlanFactory;
import com.fitnesstracker.model.NutritionPlan;
import javax.swing.*;
import java.awt.*;

// Demonstrates Factory Pattern: NutritionPlanFactory creates different plan types dynamically
// Based on user parameters and selected plan type

public class NutritionPanel extends JPanel {
    
    private JComboBox<String> planTypeCombo;
    private JSpinner weightSpinner;
    private JSpinner heightSpinner;
    private JSpinner ageSpinner;
    private JComboBox<String> genderCombo;
    private JTextArea resultsArea;
    
    public NutritionPanel() {
        setBackground(MainFrame.BG_MEDIUM);
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(MainFrame.BG_MEDIUM);
        
        JLabel titleLabel = new JLabel("Nutrition Plan Generator");
        titleLabel.setFont(MainFrame.FONT_HEADER);
        titleLabel.setForeground(MainFrame.TEXT_PRIMARY);
        titlePanel.add(titleLabel);
        
        add(titlePanel, BorderLayout.NORTH);
        
        // Input panel
        JScrollPane inputPanel = createModernInputPanel();
        add(inputPanel, BorderLayout.WEST);
        
        // Results panel
        JPanel resultsPanel = createModernResultsPanel();
        add(resultsPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = createModernButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
  private JScrollPane createModernInputPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(MainFrame.BG_DARK);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(MainFrame.ACCENT_GREEN, 2),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));

    addSectionTitle(panel, "Your Information");

    planTypeCombo = new JComboBox<>(NutritionPlanFactory.getPlanTypes());
    styleCombo(planTypeCombo);
    panel.add(createInputField("Plan Type:", planTypeCombo));
    panel.add(Box.createVerticalStrut(10));

    weightSpinner = new JSpinner(new SpinnerNumberModel(70.0, 40.0, 200.0, 0.5));
    styleSpinner(weightSpinner);
    panel.add(createInputField("Weight (kg):", weightSpinner));
    panel.add(Box.createVerticalStrut(10));

    heightSpinner = new JSpinner(new SpinnerNumberModel(170, 100, 250, 1));
    styleSpinner(heightSpinner);
    panel.add(createInputField("Height (cm):", heightSpinner));
    panel.add(Box.createVerticalStrut(10));

    ageSpinner = new JSpinner(new SpinnerNumberModel(30, 10, 100, 1));
    styleSpinner(ageSpinner);
    panel.add(createInputField("Age:", ageSpinner));
    panel.add(Box.createVerticalStrut(10));

    genderCombo = new JComboBox<>(new String[]{"MALE", "FEMALE"});
    styleCombo(genderCombo);
    panel.add(createInputField("Gender:", genderCombo));
    panel.add(Box.createVerticalStrut(20));

    JLabel infoLabel = new JLabel("<html><div style='text-align:center;'>" +
        "💡 Factory Pattern<br>creates customized<br>nutrition plans</div></html>");
    infoLabel.setFont(MainFrame.FONT_NORMAL);
    infoLabel.setForeground(MainFrame.TEXT_SECONDARY);
    infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(infoLabel);

    // Scroll pane that grows vertically
    JScrollPane scrollPane = new JScrollPane(panel);
    scrollPane.setBorder(null);
    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

    // Let it expand in BorderLayout.WEST
    scrollPane.setPreferredSize(new Dimension(380, 1000)); // high enough to show all fields
    scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smooth scrolling

    return scrollPane;
}


 
    private void addSectionTitle(JPanel panel, String title) {
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(MainFrame.ACCENT_GREEN);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(15));
    }
    
    /**
      size for all components
     */
    private JPanel createInputField(String label, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(MainFrame.BG_DARK);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70)); 
        
        JLabel l = new JLabel(label);
        l.setFont(MainFrame.FONT_NORMAL);
        l.setForeground(MainFrame.TEXT_SECONDARY);
        panel.add(l, BorderLayout.NORTH);
        
        //  component height 
        component.setPreferredSize(new Dimension(340, 40)); // Set both width and height
        component.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        panel.add(component, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
      combo box height
     */
    private void styleCombo(JComboBox<?> combo) {
        combo.setFont(MainFrame.FONT_NORMAL);
        combo.setBackground(MainFrame.BG_LIGHT);
        combo.setForeground(MainFrame.TEXT_PRIMARY);
        combo.setPreferredSize(new Dimension(340, 40)); 
    }
    
    /**
     * spinner height
     */
    private void styleSpinner(JSpinner spinner) {
        spinner.setFont(MainFrame.FONT_NORMAL);
        spinner.setPreferredSize(new Dimension(340, 40)); 
        
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setBackground(MainFrame.BG_LIGHT);
            textField.setForeground(MainFrame.TEXT_PRIMARY);
            textField.setFont(MainFrame.FONT_NORMAL);
        }
    }
    
    private JPanel createModernResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(MainFrame.BG_DARK);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(MainFrame.ACCENT_BLUE, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel("Your Personalized Plan");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(MainFrame.ACCENT_BLUE);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Consolas", Font.PLAIN, 16));
        resultsArea.setBackground(MainFrame.BG_LIGHT);
        resultsArea.setForeground(MainFrame.TEXT_PRIMARY);
        resultsArea.setCaretColor(MainFrame.ACCENT_GREEN);
        resultsArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        resultsArea.setLineWrap(false);
        
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(MainFrame.BORDER_COLOR));
        scrollPane.getViewport().setBackground(MainFrame.BG_LIGHT);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createModernButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(MainFrame.BG_MEDIUM);
        
        JButton generateButton = MainFrame.createStyledButton(" Generate Plan", MainFrame.BUTTON_GREEN);
        generateButton.setPreferredSize(new Dimension(180, 45));
        generateButton.addActionListener(e -> generateNutritionPlan());
        
        JButton clearButton = MainFrame.createStyledButton(" Clear", MainFrame.BUTTON_RED);
        clearButton.setPreferredSize(new Dimension(180, 45));
        clearButton.addActionListener(e -> resultsArea.setText(""));
        
        panel.add(generateButton);
        panel.add(clearButton);
        
        return panel;
    }
    
    private void generateNutritionPlan() {
        try {
            String planType = (String) planTypeCombo.getSelectedItem();
            double weight = (Double) weightSpinner.getValue();
            int height = (Integer) heightSpinner.getValue();
            int age = (Integer) ageSpinner.getValue();
            String gender = (String) genderCombo.getSelectedItem();
            
            NutritionPlan plan = NutritionPlanFactory.createNutritionPlan(
                planType, weight, height, age, gender);
            
            StringBuilder result = new StringBuilder();
            result.append("╔═══════════════════════════════════════════════════════════╗\n");
            result.append("║        PERSONALIZED NUTRITION PLAN                        ║\n");
            result.append("╚═══════════════════════════════════════════════════════════╝\n\n");
            
            result.append(String.format("Plan Type: %s\n", plan.getPlanType()));
            result.append(String.format(" Profile: %.1f kg | %d cm | %d years | %s\n\n", 
                weight, height, age, gender));
            
            result.append("═══════════════════════════════════════════════════════════\n");
            result.append("               DAILY NUTRITIONAL TARGETS                   \n");
            result.append("═══════════════════════════════════════════════════════════\n\n");
            
            result.append(String.format("Total Calories:     %,.0f kcal/day\n\n", plan.getDailyCalories()));
            
            result.append(String.format(" Protein:            %.0fg  (%.0f kcal)  [%.1f%%]\n", 
                plan.getProteinGrams(), 
                plan.getProteinGrams() * 4,
                (plan.getProteinGrams() * 4 / plan.getDailyCalories() * 100)));
            
            result.append(String.format("Carbohydrates:      %.0fg  (%.0f kcal)  [%.1f%%]\n", 
                plan.getCarbsGrams(), 
                plan.getCarbsGrams() * 4,
                (plan.getCarbsGrams() * 4 / plan.getDailyCalories() * 100)));
            
            result.append(String.format(" Fats:               %.0fg  (%.0f kcal)  [%.1f%%]\n\n", 
                plan.getFatsGrams(), 
                plan.getFatsGrams() * 9,
                (plan.getFatsGrams() * 9 / plan.getDailyCalories() * 100)));
            
            result.append("═══════════════════════════════════════════════════════════\n");
            result.append("                    DESCRIPTION                            \n");
            result.append("═══════════════════════════════════════════════════════════\n\n");
            result.append(plan.getDescription()).append("\n\n");
            
            result.append("This plan uses the Factory Pattern to create customized\n");
            result.append("   nutrition strategies based on your specific goals!\n");
            
            resultsArea.setText(result.toString());
            resultsArea.setCaretPosition(0);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                " Error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}