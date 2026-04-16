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
            drawCenterPilesUI(g, panelWidth / 2 - 40, panelHeight - 150);
        }
    }

    /**
     * Draw the pickaxe and the discard pile in the center of the screen
     */
    private void drawCenterPilesUI(Graphics g, int x, int y) {
        // Drawing of the deck (face down card)
        g.setColor(new Color(0, 85, 164)); // Blue UTBM
        g.fillRoundRect(x - 100, y, 80, 110, 15, 15);
        g.setColor(Color.WHITE);
        g.drawString("DECK (" + lib.getCardNumber() + ")", x - 95, y + 60);

        // Drawing the discard (Last graveward card)
        if (!graveward.isEmpty()) {
            Card topGrave = graveward.get(graveward.size() - 1);
            topGrave.drawCardUI(g, x + 20, y, 80, 110);
        } else {
            g.setColor(Color.LIGHT_GRAY);
            g.drawRoundRect(x + 20, y, 80, 110, 15, 15);
            g.drawString("EMPTY", x + 40, y + 60);
        }
    }
}
