package ui;

import javax.swing.*;
import game.Card;
import game.Player;
import java.awt.*;
import java.util.ArrayList;

public class PlayerBoardView extends JPanel {
    private Player player;
    private ArrayList<CardView> cardViews;

    public PlayerBoardView(Player player) {
        this.player = player;
        this.cardViews = new ArrayList<>();
        
        // we set up the grid with the players cards 
        setLayout(new GridLayout(player.getRaws(), player.getColumns(), 10, 10)); 
        setBorder(BorderFactory.createTitledBorder("Board of " + player.toString()));

        initializeGrid();
    }

    private void initializeGrid() {
        for (int r = 0; r < player.getRaws(); r++) {
            for (int c = 0; c < player.getColumns(); c++) {
                Card card = player.getCard(r, c);
                
                if (card != null) {
                    CardView cv = new CardView(card.getData());
                    
                    // if the card is revealed it is displayed face up
                    if (card.isVisible()) {
                        cv.setFaceUp(true);
                    }

                    cardViews.add(cv);
                    add(cv);
                }
            }
        }
    }
}