package ui; 

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Represents the window for displaying game rules.
 */
public class RulesWindow extends JFrame {

    /**
     * Constructor for the RulesWindow, which initializes the rules window.
     */
    public RulesWindow() {
        setTitle("UTBM Skyjo - Games Rules");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Games Rules", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22)); //UPDATE POLICE 
        mainPanel.add(title, BorderLayout.NORTH);

        JTextArea rulesText = new JTextArea();
        rulesText.setEditable(false);
        rulesText.setLineWrap(true);
        rulesText.setWrapStyleWord(true);
        rulesText.setFont(new Font("Segoe UI", Font.PLAIN, 14));  //UPDATE POLICE 
        
        rulesText.setText(
            "Welcome to UTBM SKYJO \n\n" +
            "1. GOAL OF THE GAME :\n" +
            "Have as few points as possible at the end of the game (except in Invert Mode).\n\n" +
            "2. PROGRESS :\n" +
            "- Each player has a board of 12 cards (3x4).\n" +
            "- Each turn, draw one card or take the discard.\n" +
            "- You then have 2 possibilities :\n" +
            "- You can exchange a card from your deck for the card you drew in the stock pile or in the discard.\n" +
            "- Or you don't want the card and you put it in the discard but you have to return one of your card face down.\n\n" +
            "3. HOW TO FINISH A ROUND :\n" +
            "- When you turn you last card face down, you end the round and you have to finish the round (so the others players have more than a chance to change their cards).\n\n"+
            "4. SPECIALS MODS :\n" +
            "- Short Mode : the game stops at the end of a round.\n" +
            "- Invert Mode : the goal is to achieve a score of 100 first !\n" +
            "- Invert Short Mode : the game stops at the end of a round but you need the biggest score !"
        );

        JScrollPane scrollPane = new JScrollPane(rulesText);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // button to close the rule window 
        JButton btnClose = new JButton("OK !");
        btnClose.addActionListener(e -> this.dispose());
        mainPanel.add(btnClose, BorderLayout.SOUTH);

        add(mainPanel);
        setLocationRelativeTo(null);
    }
}