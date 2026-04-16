package game.gamerule;

import game.Player;

/**
 * A variant of Skyjo that plays only one short round instead of continuing until a score limit.
 * Uses the same rules as the parent Skyjo class but:
 * - Resets scores to 0 at the beginning.
 * - Plays exactly one round.
 * - Then updates scores and immediately ends the game.
 */
public class ShortSkyjo extends Skyjo {

    /**
     * Default constructor for a ShortSkyjo game.
     * Creates a 2‑player game with standard hand size (3×4) and inherits Skyjo behavior.
     */
    public ShortSkyjo() {
        super();
    }

    /**
     * Configurable constructor for a ShortSkyjo game.
     *
     * @param NbPlayer      number of players
     * @param line          number of rows in each player's hand grid
     * @param column        number of columns in each player's hand grid
     * @param listOfHuman   boolean array indicating which players are human (true = human, false = AI)
     */
    public ShortSkyjo(int line, int column, boolean[] listOfHuman) {
        super(line, column, listOfHuman);
    }

    /**
     * Runs a single round of ShortSkyjo:
     * - Resets all scores to 0.
     * - Plays one round.
     * - Updates scores.
     * - Ends the game immediately (no multiple rounds).
     */
    @Override
    public void game() {
        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            this.scoreList[i] = 0;
        }
        this.round();
        this.updateScore();
        this.endGame();
    }

    /* PAREIL EST CE QUE TU PEUX REGARDER AUSSI ET VOIR SI C'EST UTILE 
    */
    public boolean isGameOver() {
        for (Player p : playerList) {
            boolean allVisible = true;
            for(int r=0; r<p.getRaws(); r++) {
                for(int c=0; c<p.getColumns(); c++) {
                    if(!p.getCard(r,c).isVisible()) allVisible = false;
                }
            }
            if(allVisible) {
                return true;
            }
        }
        return false;
    }
}
