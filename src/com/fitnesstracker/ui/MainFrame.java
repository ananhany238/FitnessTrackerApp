package com.fitnesstracker.ui;

import javax.swing.*;
import java.awt.*;

/**
 * MainFrame - Main application window with dark theme
 */
public class MainFrame extends JFrame {
    
    // Dark theme with green and blue
    public static final Color BG_DARK = new Color(18, 18, 18);
    public static final Color BG_MEDIUM = new Color(30, 30, 30);
    public static final Color BG_LIGHT = new Color(45, 45, 45);
    public static final Color ACCENT_GREEN = new Color(0, 255, 136);
    public static final Color ACCENT_BLUE = new Color(0, 191, 255);
    public static final Color TEXT_PRIMARY = new Color(255, 255, 255);
    public static final Color TEXT_SECONDARY = new Color(180, 180, 180);
    public static final Color BUTTON_GREEN = new Color(0, 200, 100);
    public static final Color BUTTON_BLUE = new Color(0, 150, 255);
    public static final Color BUTTON_RED = new Color(255, 70, 70);
    public static final Color BORDER_COLOR = new Color(60, 60, 60);
    
    //  fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 16);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 14);
    
    private JTabbedPane tabbedPane;
    
    public MainFrame() {
        initializeFrame();
        setDarkTheme();
        initializeComponents();
    }
    
    private void initializeFrame() {
        setTitle("Fitness Tracker Pro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 900);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(1000, 700));
        
        // Set dark background
        getContentPane().setBackground(BG_DARK);
    }
    
    /**
     * Set dark theme for the entire application
     */
    private void setDarkTheme() {
        try {
           
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            // Customize UI defaults for dark theme
            UIManager.put("control", BG_MEDIUM);
            UIManager.put("info", BG_LIGHT);
            UIManager.put("nimbusBase", BG_DARK);
            UIManager.put("nimbusAlertYellow", ACCENT_GREEN);
            UIManager.put("nimbusDisabledText", TEXT_SECONDARY);
            UIManager.put("nimbusFocus", ACCENT_BLUE);
            UIManager.put("nimbusGreen", ACCENT_GREEN);
            UIManager.put("nimbusInfoBlue", ACCENT_BLUE);
            UIManager.put("nimbusLightBackground", BG_MEDIUM);
            UIManager.put("nimbusSelectionBackground", ACCENT_BLUE);
            UIManager.put("text", TEXT_PRIMARY);
            
            // Table styling
            UIManager.put("Table.background", BG_MEDIUM);
            UIManager.put("Table.foreground", TEXT_PRIMARY);
            UIManager.put("Table.gridColor", BORDER_COLOR);
            UIManager.put("Table.selectionBackground", ACCENT_BLUE);
            UIManager.put("Table.selectionForeground", Color.WHITE);
            UIManager.put("TableHeader.background", BG_DARK);
            UIManager.put("TableHeader.foreground", ACCENT_GREEN);
            
            // Panel styling
            UIManager.put("Panel.background", BG_MEDIUM);
            
            // Text component styling
            UIManager.put("TextField.background", BG_LIGHT);
            UIManager.put("TextField.foreground", TEXT_PRIMARY);
            UIManager.put("TextArea.background", BG_LIGHT);
            UIManager.put("TextArea.foreground", TEXT_PRIMARY);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initializeComponents() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBackground(BG_DARK);
        
        // Header panel with gradient effect
        JPanel headerPanel = createModernHeaderPanel();
        contentPane.add(headerPanel, BorderLayout.NORTH);
        
        //  tabbed pane
        tabbedPane = createModernTabbedPane();
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        
        //  footer
        JPanel footerPanel = createModernFooterPanel();
        contentPane.add(footerPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Create header with gradient effect
     */
    private JPanel createModernHeaderPanel() {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, BG_DARK,
                    0, getHeight(), BG_MEDIUM
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Bottom accent line
                g2d.setColor(ACCENT_GREEN);
                g2d.fillRect(0, getHeight() - 3, getWidth(), 3);
                
            }
            
        };
          
        
        headerPanel.setPreferredSize(new Dimension(0, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setOpaque(false);
        
        // Title with icon
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        titlePanel.setOpaque(false);
        
        // App icon 
        JLabel iconLabel = new JLabel("💪");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        titlePanel.add(iconLabel);
        
        JLabel titleLabel = new JLabel("FITNESS TRACKER PRO");
        titleLabel.setFont(FONT_TITLE);
        titleLabel.setForeground(TEXT_PRIMARY);
        titlePanel.add(titleLabel);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        
        // Version label
        JLabel versionLabel = new JLabel("");
        versionLabel.setFont(FONT_SMALL);
        versionLabel.setForeground(TEXT_SECONDARY);
        versionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        headerPanel.add(versionLabel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    /**
     * Create styled tabbed pane
     */
    private JTabbedPane createModernTabbedPane() {
        JTabbedPane pane = new JTabbedPane();
        pane.setFont(FONT_NORMAL);
        pane.setBackground(BG_MEDIUM);
        pane.setForeground(TEXT_PRIMARY);
        pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        
        // Add tabs with icons
        pane.addTab("   Dashboard  ", new DashboardPanel());
        pane.addTab("   Log Activity  ", new ActivityLogPanel());
        pane.addTab("  Goals  ", new GoalsPanel());
        pane.addTab("  Nutrition  ", new NutritionPanel());
        pane.addTab("   Workout  ", new WorkoutGeneratorPanel());
        
        // Style the tabs
        pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        return pane;
    }
    
    /**
     * Create footer panel
     */
    private JPanel createModernFooterPanel() {
        JPanel footerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                // Top accent line
                g2d.setColor(ACCENT_BLUE);
                g2d.fillRect(0, 0, getWidth(), 2);
                
                // Background
                g2d.setColor(BG_DARK);
                g2d.fillRect(0, 2, getWidth(), getHeight() - 2);
            }
        };
        
        footerPanel.setPreferredSize(new Dimension(0, 35));
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 8));
        footerPanel.setOpaque(false);
        
        // Pattern labels with colors
        addPatternLabel(footerPanel, " Singleton", ACCENT_BLUE);
        addPatternLabel(footerPanel, " Factory", ACCENT_GREEN);
        addPatternLabel(footerPanel, " Observer", ACCENT_BLUE);
        addPatternLabel(footerPanel, " Strategy", ACCENT_GREEN);
        addPatternLabel(footerPanel, " Decorator", ACCENT_BLUE);
        
        return footerPanel;
    }
    
    /**
     * Helper method to add pattern labels to footer
     */
    private void addPatternLabel(JPanel panel, String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_SMALL);
        label.setForeground(color);
        panel.add(label);
    }
    
    /**
     * Create a styled button with consistent look
     */
    public static JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(FONT_NORMAL);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
        
        // Rounded corners effect
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
}