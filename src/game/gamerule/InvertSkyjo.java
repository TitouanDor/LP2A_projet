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
    }

    /**
     * Configurable constructor for an InvertSkyjo game.
     *
     * @param NbPlayer      number of players
     * @param line          number of rows in each player's hand grid
     * @param column        number of columns in each player's hand grid
     * @param listOfHuman   boolean array indicating which players are human (true = human, false = AI)
     */
    public InvertSkyjo(int line, int column, boolean[] listOfHuman) {
        super(line, column, listOfHuman);
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

    // PAREIL EST CE QUE TU PEUX REGARDER 
    public void endRoundUI() {
        this.updateScore(); 
        // plus tard ajouter ici un appel vers l'UI pour afficher les scores et proposer le round suivant.
    }
}
