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
import javax.swing.JButton;
import javax.swing.JCheckBox;

/**
 * Represents the main menu window for the UTBM Skyjo game.
 */
public class MenuWindow extends JFrame {
    private JButton btnClassic, btnShort, btnInvert, btnInvertShort, btnQuit, btnRules;
    private JCheckBox checkHuman1, checkHuman2;

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

        // choose if you are a human or not 
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setOpaque(false);

        JPanel playersPanel = new JPanel();
        playersPanel.setOpaque(false);
        checkHuman1 = new JCheckBox("Player 1 human ?", true);
        checkHuman2 = new JCheckBox("Player 2 human ?", true);
        playersPanel.add(checkHuman1);
        playersPanel.add(checkHuman2);

        centerPanel.add(new JLabel ("Configure your player", SwingConstants.CENTER));
        centerPanel.add(playersPanel);

        //button rules 
        btnRules = createStyledButton("Game Rules");
        btnRules.setBackground(new Color(240,74,43));
        centerPanel.add(btnRules);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

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
}