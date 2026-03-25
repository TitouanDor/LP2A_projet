package game;

import java.util.ArrayList;

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
        this.playerList = new ArrayList<Player>(this.numberOfPlayer);
        for (int i = 0; i < this.numberOfPlayer; i++) {
            this.playerList.add(new Player(false, 3, 4));
        }
        this.graveward = new ArrayList<Card>();
        this.lib = new Library();
        this.isUiActive = false;
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
     * Draws the board in a graphical or interactive UI mode.
     * 
     * @return no return value
     */
    protected void drawBoardUi() {
        // PUT HOW TO DRAW THE UI HERE
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
}
