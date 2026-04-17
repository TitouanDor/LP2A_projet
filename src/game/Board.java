package game;

import java.util.ArrayList;
import java.awt.*;

/**
 * Represents the game board, including players, a library of cards,
 * and a graveyard (discard pile).
 * Manages the current state and can print or draw the board in text or UI mode.
 */
public class Board {

    /** The central library of cards used in the game. */
    protected Library lib;

    /** Graveyard (discard pile) where used cards are placed. */
    protected ArrayList<Card> graveward; //MAGIC THE GATHERING

    /** The number of players in the current game. */
    private int numberOfPlayer;

    /** ID of the player whose turn it is. */
    protected int id_player = 0;

    /** List of all players currently on the board. */
    protected ArrayList<Player> playerList;

    /** True if a graphical UI is currently active, false otherwise. */
    protected boolean isUiActive;

    /**
     * Default board constructor.
     * Creates a game with 2 players, each with a 3×4 hand grid.
     * Initializes the library and an empty graveyard; UI is disabled by default.
     */
    public Board() {
        this.numberOfPlayer = 2;
        this.playerList = new ArrayList<Player>(this.getNumberOfPlayer());
        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            this.playerList.add(new Player(false, 3, 4));
        }
        this.graveward = new ArrayList<Card>();
        this.lib = new Library();
        this.isUiActive = true;
        for (Player p : playerList) {
            p.setName("Player " + (playerList.indexOf(p) + 1));
        }
    }

    // À ajouter dans Board.java
    public void setUiActive(boolean active) {
        this.isUiActive = active;
    }
    

    /**
     * Configurable board constructor.
     * Creates a game with the specified number of players, hand dimensions,
     * and player types (human vs AI).
     *
     * @param NbPlayer      number of players
     * @param line          number of rows in each player's hand grid
     * @param column        number of columns in each player's hand grid
     * @param listOfHuman   boolean array indicating which players are human (true = human, false = AI)
     */
    public Board(int NbPlayer, int line, int column, boolean[] listOfHuman) {
        this.numberOfPlayer = NbPlayer;
        this.playerList = new ArrayList<Player>(this.numberOfPlayer);
        for (int i = 0; i < this.numberOfPlayer; i++) {
            this.playerList.add(new Player(listOfHuman[i], line, column));
            this.playerList.get(i).setName("Player " + (i + 1));
        }
        this.graveward = new ArrayList<Card>();
        this.lib = new Library();
        this.isUiActive = false;
    }

    /**
     * Returns the number of players in this game.
     *
     * @return number of players
     */
    public int getNumberOfPlayer() {
        return this.numberOfPlayer;
    }

    /**
     * Access a specific player by index.
     * Required for GameWindow.
     */
    public Player getPlayer(int index) {
        if (index >= 0 && index < this.playerList.size()) {
            return this.playerList.get(index);
        }
        return null;
    }

    /**
     * Access the current player.
     * Required for GameWindow.
     */
    public Player getCurrentPlayer() {
        return this.playerList.get(this.id_player);
    }

    /**
     * Prints the current contents of the graveyard (discard pile) to standard output.
     * Prints the value of each card in the graveyard, separated by ";".
     * 
     * @return no return value
     */
    protected void printGraveward() {
        for (Card card : this.graveward) {
            System.out.print(card.getValue() + ";");
        }
        System.out.println();
    }

    /**
     * Draws the board in text/console mode without a special UI.
     * Clears the console, prints the library reference and the graveyard,
     * and shows the last card in the graveyard if the graveyard is not empty.
     * 
     * @return no return value
     */
    protected void drawBoardWithoutUi() {
        System.out.print("\033[H\033[2J"); // TO CLEAR THE CONSOLE
        System.out.println();
        System.out.print("Lib : #\t Grave : ");
        int gravewardSize = this.graveward.size();
        if (gravewardSize != 0) {
            Card card = this.graveward.get(gravewardSize - 1);
            System.out.println(card.getValue());
        } else {
            System.out.println("None");
        }
    }
    
    // LOGIQUE UI AVEC TRANSFERT DE PLAYERBOARDVIEW (A VERIFIER AVANT SUPPRESSION DE PLAYERBOARDVIEW)

    public void drawAllPlayersUI(Graphics g, int panelWidth, int panelHeight) {
        int nbPlayers = playerList.size();
        int spacing = 20;
        int playerAreaWidth = (panelWidth - (spacing * (nbPlayers + 1))) / nbPlayers;

        for (int i = 0; i < nbPlayers; i++) {
            Player p = playerList.get(i);
            int startX = spacing + i * (playerAreaWidth + spacing);
            // we draw the board
            p.drawHandUI(g, startX, 50, 80, 110);  
        }
        drawCenterPilesUI(g, panelWidth / 2 , panelHeight - 150);

        // 3. NOUVEAU : Dessin de la carte piochée
    // On vérifie si l'instance actuelle est bien un Skyjo pour accéder à cardInHand
    if (this instanceof game.gamerule.Skyjo) {
        game.gamerule.Skyjo skyjoGame = (game.gamerule.Skyjo) this;
        Card inHand = skyjoGame.getCardInHand();

        if (inHand != null) {
            int cardW = 80;
            int cardH = 110;
            // On la place au dessus des piles centrales (y - 150)
            int handX = (panelWidth / 2) - (cardW / 2);
            int handY = panelHeight - 320; 

            // Small visual effect: a shadow or an outline to highlight it
            g.setColor(new Color(0, 0, 0, 50));
            g.fillRoundRect(handX + 4, handY + 4, cardW, cardH, 15, 15);

            inHand.reveal(); 
            inHand.drawCardUI(g, handX, handY, cardW, cardH);
            }
        }
    }

    /**
     * Draw the pickaxe and the discard pile in the center of the screen
     */
    private void drawCenterPilesUI(Graphics g, int centerX , int y) {
        // Drawing of the deck (face down card)
        int deckX = centerX - 100;
        g.setColor(new Color(0, 85, 164)); 
        g.fillRoundRect(deckX, y, 80, 110, 15, 15);
        g.setColor(Color.WHITE);
        g.drawString("DECK (" + lib.getCardNumber() + ")", deckX + 5, y + 60);

        // Drawing the discard (Last graveward card)
        int graveX = centerX + 20;
        if (!graveward.isEmpty()) {
            Card topGrave = graveward.get(graveward.size() - 1);
            topGrave.drawCardUI(g, graveX, y, 80, 110);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.drawRoundRect(graveX, y, 80, 110, 15, 15);
            g.drawString("EMPTY", graveX + 20, y + 60);
        }
    }

    /**
    * Browse all the players to see if any of them received a click on a map.
    */
    public Object[] getClickedCardData(int mouseX, int mouseY, int panelWidth) {
        int nbPlayers = playerList.size();
        int spacing = 30;
        int playerAreaWidth = (panelWidth - (spacing * (nbPlayers + 1))) / nbPlayers;

        for (int i = 0; i < nbPlayers; i++) {
            int startX = spacing + i * (playerAreaWidth + spacing);
            int[] cardCoords = playerList.get(i).getCardAt(mouseX, mouseY, startX, 50, 80, 110);
        
            if (cardCoords != null) {
                // we send back the player concerned and the map’s contact details
                return new Object[]{ playerList.get(i), cardCoords[0], cardCoords[1] };
            }
        }
        return null;
    }

    /**
    * Check if the click is on the pickaxe (Deck)
    */
    public boolean isDeckClicked(int x, int y, int panelWidth, int panelHeight) {
        int deckX = (panelWidth / 2) - 100; 
        int deckY = panelHeight - 150;
        return (x >= deckX && x <= deckX + 80 && y >= deckY && y <= deckY + 110);
    }

    /**
    * Check if the click is on the discard (Grave)
    */
    public boolean isGraveClicked(int x, int y, int panelWidth, int panelHeight) {
        int graveX = (panelWidth / 2) + 20; 
        int graveY = panelHeight - 150;
        return (x >= graveX && x <= graveX + 80 && y >= graveY && y <= graveY + 110);
    }
}
