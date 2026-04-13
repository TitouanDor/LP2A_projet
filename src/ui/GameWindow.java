package ui;

import game.Player;
import game.gamerule.Skyjo;
import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private Skyjo game;

    public GameWindow(Skyjo game) {
        this.game = game;
        
        // initialisation game window
        setTitle("UTBM Skyjo - " + game.getClass().getSimpleName());
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(230, 230, 230));

        // player grid 
        JPanel playersPanel = new JPanel(new GridLayout(1, game.getNumberOfPlayer(), 20, 0));
        playersPanel.setOpaque(false);
        playersPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < game.getNumberOfPlayer(); i++) {
            Player p = game.getPlayer(i);
            playersPanel.add(new PlayerBoardView(p));
        }
        add(playersPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(0, 85, 164));
        southPanel.setPreferredSize(new Dimension(0, 50));

        JLabel statusLabel = new JLabel("round of: " + game.getCurrentPlayer().toString(), SwingConstants.CENTER);
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        southPanel.add(statusLabel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        // 3. ZONE SUPÉRIEURE : Pioche et Défausse (Placeholders pour le moment)
        JPanel northPanel = new JPanel();
        northPanel.add(new JLabel("Draw and Discard "));
        add(northPanel, BorderLayout.NORTH);

        // Centrer et afficher
        setLocationRelativeTo(null);
        setVisible(true);
    }
}