package game.gamerule;

import game.Player;
import game.Bot;

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
     * @param aiLevel         the level of the AI players

     */
    public ShortSkyjo(int line, int column, boolean[] listOfHuman, int aiLevel) {
        super(line, column, listOfHuman, aiLevel);
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

    @Override
    protected void advanceTurn() {
        while (true) {
            Player p = this.getCurrentPlayer();
            this.updatePlayerHand(p);

            if (this.isRoundFinish()) {
                this.updateScore();
                this.currentStep = GameStep.GAME_OVER;
                this.endGame();
                return;
            }

            this.id_player = (this.id_player + 1) % this.getNumberOfPlayer();
            this.currentStep = GameStep.START_TURN;

            Player current = this.getCurrentPlayer();

            if (current.isHumain()) {
                return; // on s'arrête, l'UI attend l'action du joueur humain
            }

            if (current instanceof Bot) {
                Bot b = (Bot) current;
                b.turn(this.lib, this.graveward, this.MinimizeScore); // the bot plays his turn by himself
            }
        }
    }

    /**
     * Checks if the game is over by verifying if any player has all their cards visible.
     * 
     * @return true if the game is over (at least one player has all cards visible), false otherwise
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
