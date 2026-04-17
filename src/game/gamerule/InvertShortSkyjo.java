package game.gamerule;

import game.Player;

/**
 * A short variant of InvertSkyjo that plays only a single round.
 * Uses inverted Skyjo scoring, but:
 * - Resets all scores to 0 at the start.
 * - Plays exactly one round.
 * - Updates scores and immediately ends the game.
 */
public class InvertShortSkyjo extends InvertSkyjo {

    /**
     * Default constructor for an InvertShortSkyjo game.
     * Creates a 2‑player game with standard hand size (3×4)
     * and inherits InvertSkyjo behavior.
     */
    public InvertShortSkyjo() {
        super();
    }

    /**
     * Configurable constructor for an InvertShortSkyjo game.
     *
     * @param NbPlayer    number of players
     * @param line        number of rows in each player's hand grid
     * @param column      number of columns in each player's hand grid
     * @param listOfHuman boolean array indicating which players are human (true = human, false = AI)
     */
    public InvertShortSkyjo(int line, int column, boolean[] listOfHuman) {
        super(line, column, listOfHuman);
    }

    /**
     * Runs a single round of InvertShortSkyjo:
     * - Resets all scores to 0.
     * - Plays one round.
     * - Updates scores using inverted rules.
     * - Ends the game immediately (no further rounds).
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
    /**
     * Advances the game to the next turn.
     */
    protected void advanceTurn() {
        Player p = this.getCurrentPlayer();
        this.updatePlayerHand(p);

        // check if it is the end of the game 
        if (this.isRoundFinish()) {
            this.updateScore();
            this.endGame();
        } else {
            // next player
            
            this.id_player = (this.id_player + 1) % this.getNumberOfPlayer();
            this.currentStep = GameStep.START_TURN;

            // If it’s an AI, you automate your turn.
            if (!this.getCurrentPlayer().isHumain()) {
                Player bot = this.getCurrentPlayer();
                this.playAiTurn(bot);
            }
        }
    }
}

