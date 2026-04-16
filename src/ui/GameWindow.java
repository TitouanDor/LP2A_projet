package ui;

import game.Board;
import game.Player;
import game.gamerule.Skyjo;
import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private Board game;
    private GamePanel gamePanel;

    public GameWindow(Skyjo game) {
        this.game = game;
        
        // window initialisation
        setTitle("UTBM Skyjo - " + game.getClass().getSimpleName());
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 230, 230));

        this.gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        // south panal
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(0, 85, 164)); 
        southPanel.setPreferredSize(new Dimension(0, 50));

        JLabel statusLabel = new JLabel("Turn of: " + game.getCurrentPlayer().toString(), SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        southPanel.add(statusLabel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // center and print 
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Custom internal panel that manages the graphical rendering of the game. It delegates the drawing to the Board class.
     */
    private class GamePanel extends JPanel {
        public GamePanel() {
            setOpaque(false); 
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // The centralized drawing method in Board.java is called
            //She will draw the players, their cards, the drawing board, and the discard.
            if (game != null) {
                game.drawAllPlayersUI(g, getWidth(), getHeight());
            }
        }
    }
    
    /**
     * method to refresh the display after an action
     */
    public void refresh() {
        repaint();
    }
}