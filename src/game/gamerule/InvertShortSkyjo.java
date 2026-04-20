package game.gamerule;

import game.Bot;
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
        this.MinimizeScore = false; // In InvertShortSkyjo, bots should try to maximize their score, not minimize it
        System.out.println("Minimize Score: " + this.MinimizeScore);
    }

    /**
     * Configurable constructor for an InvertShortSkyjo game.
     *
     * @param NbPlayer    number of players
     * @param line        number of rows in each player's hand grid
     * @param column      number of columns in each player's hand grid
     * @param listOfHuman boolean array indicating which players are human (true = human, false = AI)
     * @param aiLevel         the level of the AI players
     */
    public InvertShortSkyjo(int line, int column, boolean[] listOfHuman, int aiLevel) {
        super(line, column, listOfHuman, aiLevel);
        this.MinimizeScore = false; // In InvertShortSkyjo, bots should try to maximize their score, not minimize it
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
}

