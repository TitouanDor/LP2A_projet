package ui;

import javax.swing.border.EmptyBorder; 
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Cursor;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.*;

/**
 * Represents the main menu window for the UTBM Skyjo game.
 */
public class MenuWindow extends JFrame {
    /** Buttons for different game modes */
    private JButton btnClassic, btnShort, btnInvert, btnInvertShort, btnQuit, btnRules;

    /** Checkboxes for selecting human players */
    private JCheckBox checkHuman1, checkHuman2;
    
    /** Combo box for selecting AI level */
    private JComboBox<String> aiLevelSelector;

    /** Label for AI level selection */
    private JLabel aiLabel;

    /**
     * Constructor for the MenuWindow, which initializes the main menu window.
     */
    public MenuWindow() {
        setTitle("UTBM Skyjo - Main Menu");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //main panel 
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(30, 50, 30, 50));
        mainPanel.setBackground(new Color(245, 245, 245)); // UPDATE COLOR AFTER 

        //title 
        JLabel title = new JLabel("Welcome to the UTBM Skyjo", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20)); //UPDATE POLICE ECRITURE 
        title.setForeground(new Color(0, 85, 164));
        mainPanel.add(title, BorderLayout.NORTH);

        //part with IA button 
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.setOpaque(false);

        JLabel configLabel = new JLabel("Configure your players");
        configLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        configLabel.setForeground(new Color(0, 85, 164));

        JPanel playersPanel = new JPanel(); 
        playersPanel.setOpaque(false);
        playersPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 

        //part for check if you are a humain or not 
        checkHuman1 = new JCheckBox("Player 1 human ?", true);
        checkHuman2 = new JCheckBox("Player 2 human ?", true);
        playersPanel.add(checkHuman1);
        playersPanel.add(checkHuman2); 

        aiLabel = new JLabel("AI level :");
        aiLabel.setForeground(new Color(0, 85, 164)); 
        aiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] levels = {"Level 0 (Easy)", "Level 1 (Normal)", "Level 2 (Expert)"};
        aiLevelSelector = new JComboBox<>(levels);
        aiLevelSelector.setMaximumSize(new Dimension(200, 30));
        aiLevelSelector.setAlignmentX(Component.CENTER_ALIGNMENT);

        configPanel.add(configLabel);      
        configPanel.add(playersPanel);     
        configPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        configPanel.add(aiLabel);
        configPanel.add(aiLevelSelector);
        configPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Button Rules
        btnRules = createStyledButton("Game Rules");
        btnRules.setBackground(new Color(240, 74, 43));
        btnRules.setAlignmentX(Component.CENTER_ALIGNMENT);
        configPanel.add(btnRules);

        mainPanel.add(configPanel, BorderLayout.CENTER);

        // mode buttons 
        JPanel southPanel = new JPanel(new BorderLayout(10,10));
        southPanel.setOpaque(false);

        JPanel gridButtons = new JPanel(new GridLayout(2,2,15,15));
        gridButtons.setOpaque(false);

        btnClassic = createStyledButton("Classic Mode");
        btnShort = createStyledButton("Short Mode (1 round)");
        btnInvert = createStyledButton("Invert Mode");
        btnInvertShort = createStyledButton("Invert Short Mode (1 round)");
        
        gridButtons.add(btnClassic);
        gridButtons.add(btnShort);
        gridButtons.add(btnInvert);
        gridButtons.add(btnInvertShort);

        btnQuit = createStyledButton("Quit the game");
        btnQuit.setForeground(new Color(150, 0,0));

        southPanel.add(gridButtons, BorderLayout.CENTER);
        southPanel.add(btnQuit, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);
    
        add(mainPanel);
        setLocationRelativeTo(null); // center the window 
        setVisible(true);
    }

    /**
     * Helper method to create a styled JButton with consistent appearance across the menu.
     * 
     * @param text the text to display on the button
     * 
     * @return the created styled button
     */
    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14)); //UDPATE POLICE AFTER 
        btn.setBackground(Color.WHITE); //UPDATE COLOR AFTER 
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Method to set the ActionListener for all buttons in the menu. This allows the controller to handle user interactions with the menu.
     * 
     * @param listener the ActionListener to set for the buttons
     */
    public void setMenuListener(ActionListener listener) {
        btnClassic.addActionListener(listener);
        btnShort.addActionListener(listener);
        btnInvert.addActionListener(listener);
        btnInvertShort.addActionListener(listener);
        btnQuit.addActionListener(listener);
        btnRules.addActionListener(listener);
    }

    /**
     * Getter for the selected player types (human or AI) based on the state of the checkboxes in the menu.
     * 
     * @return an array of booleans indicating whether each player is human (true) or AI (false)
     */
    public boolean[] getSelectedPlayers() {
        return new boolean[]{checkHuman1.isSelected(), checkHuman2.isSelected()};
    }

    /**
     * Retrieves the difficulty level selected by the user in the AI combo box.
     * 
     * * @return the index of the selected AI level (e.g., 0 for Easy, 1 for Normal, 2 for Expert).
     */
    public int getSelectedAILevel() {
        return aiLevelSelector.getSelectedIndex();
    }
}