package game.gamerule;

import game.Player;

/**
 * A variant of Skyjo with inverted scoring rules.
 * In InvertSkyjo:
 * - The goal is to have the *highest* score at the end of the round.
 * - If the player who ends the round does *not* have the highest score among the others,
 *   their score is halved (for positive scores).
 */
public class InvertSkyjo extends Skyjo {

    /**
     * Default constructor for an InvertSkyjo game.
     * Creates a 2‑player game with standard hand size (3×4) and inherits Skyjo behavior.
     */
    public InvertSkyjo() {
        super();
        this.MinimizeScore = false; // In InvertSkyjo, bots should try to maximize their score, not minimize it
    }

    /**
     * Configurable constructor for an InvertSkyjo game.
     *
     * @param NbPlayer      number of players
     * @param line          number of rows in each player's hand grid
     * @param column        number of columns in each player's hand grid
     * @param listOfHuman   boolean array indicating which players are human (true = human, false = AI)
     * @param aiLevel         the level of the AI players
     */
    public InvertSkyjo(int line, int column, boolean[] listOfHuman, int aiLevel) {
        super(line, column, listOfHuman, aiLevel);
        this.MinimizeScore = false; // In InvertShortSkyjo, bots should try to maximize their score, not minimize it
    }

    /**
     * Updates the score at the end of a round following InvertSkyjo rules:
     * - All players reveal their hands.
     * - Compute each player’s total hand value.
     * - If the finisher does *not* have a score higher than all others,
     *   and their score is positive, it is halved.
     *
     * @return no return value (void)
     */
    @Override
    protected void updateScore() {
        Player p;
        int[] tempScore = new int[this.getNumberOfPlayer()];
        int scoreMaxWithoutWinner;
        if (id_finisher == 0) {
            scoreMaxWithoutWinner = this.playerList.get(1).getHandValue();
        } else {
            scoreMaxWithoutWinner = this.playerList.get(0).getHandValue();
        }

        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            p = this.playerList.get(i);
            p.revealHand();
            tempScore[i] = p.getHandValue();
            if (i != id_finisher && tempScore[i] > scoreMaxWithoutWinner) {
                scoreMaxWithoutWinner = tempScore[i];
            }
        }

        if (scoreMaxWithoutWinner >= tempScore[id_finisher] && tempScore[id_finisher] > 0) {
            System.out.println("Play better, your score is divided by 2");
            tempScore[id_finisher] /= 2;
        }

        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            this.scoreList[i] += tempScore[i];
        }
    }
 
    @Override
    /**
     * Checks if the game is finished by verifying if any player's score has reached or exceeded 500 points.
     * 
      * @return true if the game is finished, false otherwise
     */
    protected boolean isGameFinish() {
        for (int score : this.scoreList) {
            if (score >= 500) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ends the game by determining the overall winner
     * (player with the lowest total score).
     * Prints a victory message.
     * 
     * @return no return value
     */
    @Override
    protected void endGame() {
        int id_winner = 0;
        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            if (this.scoreList[id_winner] < this.scoreList[i]) {
                id_winner = i;
            }
        }
        Player p = this.playerList.get(id_winner);

        System.out.println("THE WINNER IS : " + p + " WITH A SCORE OF : " + this.scoreList[id_winner]);
    }
}
