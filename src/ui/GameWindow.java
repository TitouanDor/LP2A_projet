package ui;

import game.Player;
import game.gamerule.Skyjo;
import game.gamerule.GameStep;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Represents the window for displaying the game board and handling user interactions during gameplay.
 */
public class GameWindow extends JFrame {
    private Skyjo game;
    private GamePanel gamePanel;
    private JLabel statusLabel;

    public GameWindow(Skyjo game) {
        this.game = game;
        
        // window initialisation
        setTitle("UTBM Skyjo - " + game.getClass().getSimpleName());
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 230, 230));

        this.gamePanel = new GamePanel();
        add(this.gamePanel, BorderLayout.CENTER);

        // south panal
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(0, 85, 164)); 
        southPanel.setPreferredSize(new Dimension(0, 50));

        this.statusLabel = new JLabel("Turn of: " + game.getCurrentPlayer().toString(), SwingConstants.CENTER);
        this.statusLabel.setForeground(Color.WHITE);
        this.statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        southPanel.add(this.statusLabel, BorderLayout.CENTER);
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
            this.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    handleMouseClick(e.getX(), e.getY());
                }
            }); 
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

        private void handleMouseClick(int x, int y) {
            int w = getWidth();
            int h = getHeight();

            boolean clickedDeck = game.isDeckClicked(x, y, w, h);
            boolean clickedGrave = game.isGraveClicked(x, y, w, h);

            Object[] cardData = game.getClickedCardData(x, y, w);
    
            int row = -1, col = -1;
            if (cardData != null) {
                Player p = (Player) cardData[0];
                if (p == game.getCurrentPlayer()) {
                    row = (int) cardData[1];
                    col = (int) cardData[2];
                }
            }

            game.handleAction(row, col, clickedDeck, clickedGrave);
    
            refresh(); 
        }
    }
    
    /**
     * method to refresh the display after an action
     */
    public void refresh() {
        if (game instanceof Skyjo) {
            Skyjo s = (Skyjo) game;
            String playerName = s.getCurrentPlayer().toString();
        
            if (s.getStep() == GameStep.START_TURN) {
                statusLabel.setText("Turn of " + playerName + " : Draw a card (Deck or Discard)");
            } else if (s.getStep() == GameStep.CARD_PICKED) {
                statusLabel.setText(playerName + " : Click on one of your cards to exchange");
            } else if (s.getStep() == GameStep.GAME_OVER) {
                statusLabel.setText("GAME OVER");
            } else if (s.getStep() == GameStep.WAITING_FOR_REVEAL) {
                statusLabel.setText(playerName + " : Card rejected ! Reveal one of your hidden cards.");
            }
        }      
        repaint(); 
    }
}