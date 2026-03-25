package game.gamerule;

import game.Board;
import game.Card;
import game.Player;

/**
 * Implementation of the Skyjo variant built on top of the generic Board.
 * Skyjo is a card‑game where:
 * - Each player has a grid of hidden cards (e.g., 3×4).
 * - A round ends when one player reveals all their cards.
 * - Scores are accumulated until one player reaches or exceeds 100 points,
 *   at which point the player with the lowest total wins.
 *
 * This class manages:
 * - round setup ({@link #setUpRound()}),
 * - round and game loops ({@link #round()}, {@link #game()}),
 * - scoring with the special “end‑round scoring twist”.
 */
public class Skyjo extends Board {

    /** Score list indexed by player ID; scores accumulate over rounds. */
    protected int[] scoreList;

    /** ID of the player who first reveals all cards in the current round. */
    protected int id_finisher;

    /**
     * Default constructor for a Skyjo game.
     * Creates a 2‑player game with standard hand size (3×4) and initializes the score list.
     */
    public Skyjo() {
        super();
        this.scoreList = new int[this.getNumberOfPlayer()];
    }

    /**
     * Configurable constructor for a Skyjo game.
     *
     * @param NbPlayer      number of players
     * @param line          number of rows in each player's hand grid
     * @param column        number of columns in each player's hand grid
     * @param listOfHuman   boolean array indicating which players are human (true = human, false = AI)
     */
    public Skyjo(int NbPlayer, int line, int column, boolean[] listOfHuman) {
        super(NbPlayer, line, column, listOfHuman);
        this.scoreList = new int[this.getNumberOfPlayer()];
    }

    /**
     * Sets up a new round:
     * - Deals a full hand to each player.
     * - Reveals two random cards from each player’s hand.
     * - Places the first grave card by drawing from the library.
     * 
     * @return no return value
     */
    protected void setUpRound() {
        int nbCard = 0;
        for (Player player : this.playerList) {
            nbCard = player.getColumns() * player.getRaws();
            player.setHand(lib.drawSetUp(nbCard));
            Card card;
            for (int i = 0; i < 2; i++) {
                do {
                    card = player.chooseCardFromHand(true);
                } while (card.isVisible());
                card.reveal();
            }
        }
        this.graveward.add(this.lib.drawRandomCard(true));
    }

    /**
     * Checks whether the current round has finished.
     * The round ends when one player has revealed all cards in their hand.
     *
     * @return true if the round has ended; in that case, {@link #id_finisher} is set to that player’s ID
     */
    protected boolean isRoundFinish() {
        Player p;
        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            p = this.playerList.get(i);
            if (p.isHandRevealed()) {
                this.id_finisher = i;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the entire game has finished.
     * The game ends when any player’s total score reaches or exceeds 100 points.
     *
     * @return true if the game has ended
     */
    protected boolean isGameFinish() {
        for (int score : this.scoreList) {
            if (score >= 100) {
                return true;
            }
        }
        return false;
    }

    /**
     * Executes a single round of the game.
     * - Sets up the round (deals hands, reveals two cards per player, starts the grave card).
     * - Runs the turn loop until the round ends.
     * 
     * @return no return value
     */
    protected void round() {
        this.setUpRound();
        Player playingPlayer;
        int i = 0;
        while (!this.isRoundFinish()) {
            if (i >= this.getNumberOfPlayer()) {
                i = 0;
            }
            playingPlayer = this.playerList.get(i);
            if (this.isUiActive) {
                this.drawBoardUi();
            } else if (playingPlayer.isHumain()) {
                System.out.println("Player : " + i + " your turn !!");
                this.drawBoardWithoutUi();
                playingPlayer.drawConsolHand();
            }
            this.playerTrun(playingPlayer);
            i++;
        }
    }

    /**
     * Starts and runs the full Skyjo game.
     * - Resets all scores to 0.
     * - Plays rounds until the game ends (someone reaches 100+ points).
     * - After each round, updates scores and prints them.
     * - At the end, announces the winner.
     * 
     * @return no return value
     */
    public void game() {
        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            this.scoreList[i] = 0;
        }
        while (!isGameFinish()) {
            this.round();
            this.updateScore();
            this.printScore();
        }
        this.endGame();
    }

    /**
     * Updates the score at the end of a round following Skyjo rules:
     * - All players reveal their hands.
     * - Sum their card values.
     * - If the finisher doesn’t have the lowest score, their score is doubled (for positive scores).
     * 
     * @return no return value
     */
    protected void updateScore() {
        Player p;
        int[] tempScore = new int[this.getNumberOfPlayer()];
        int scoreMinWithoutWinner;
        if (id_finisher == 0) {
            scoreMinWithoutWinner = this.playerList.get(1).getHandValue();
        } else {
            scoreMinWithoutWinner = this.playerList.get(0).getHandValue();
        }

        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            p = this.playerList.get(i);
            p.revealHand();
            tempScore[i] = p.getHandValue();
            if (i != id_finisher && tempScore[i] < scoreMinWithoutWinner) {
                scoreMinWithoutWinner = tempScore[i];
            }
        }

        if (scoreMinWithoutWinner <= tempScore[id_finisher] && tempScore[id_finisher] > 0) {
            System.out.println("Play better, your score is multiplied by 2");
            tempScore[id_finisher] *= 2;
        }

        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            this.scoreList[i] += tempScore[i];
        }
    }

    /**
     * Ends the game by determining the overall winner
     * (player with the lowest total score).
     * Prints a victory message.
     * 
     * @return no return value
     */
    protected void endGame() {
        int id_winner = 0;
        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            if (this.scoreList[id_winner] > this.scoreList[i]) {
                id_winner = i;
            }
        }
        Player p = this.playerList.get(id_winner);

        System.out.println("THE WINNER IS : " + p + " WITH A SCORE OF : " + this.scoreList[id_winner]);
    }

    /**
     * Executes a single player’s turn:
     * - Player chooses to draw from the library or the graveyard.
     * - Then chooses to either:
     *   - exchange a card in their hand, or
     *   - reveal one card in their hand.
     * Finally, the player’s hand is updated (columns of equal cards may be removed depending on your rules).
     *
     * @param p the player whose turn it is
     * 
     * @return no return value
     */
    protected void playerTrun(Player p) {
        int choice;
        Card cardInPlay;
        int[] coo;
        Card cardToReplace;

        choice = p.chooseBetweenTwo("Do you want to draw the top Card of : \n\t(0) : the library\n\t(1) : the graveward");
        if (choice == 0) {
            cardInPlay = this.lib.drawRandomCard(true);
            if (p.isHumain()) {
                System.out.println(cardInPlay.getValue());
            }
        } else if (choice == 1) {
            int index = this.graveward.size() - 1;
            cardInPlay = this.graveward.get(index);
            this.graveward.remove(index);
            cardInPlay.reveal();
        } else {
            cardInPlay = null;
        }

        choice = p.chooseBetweenTwo("Do you want to : \n\t(0) : exchange the card\n\t(1) : reveal one of your hand/board");
        coo = p.chooseXY();
        if (choice == 0) {
            cardToReplace = p.exchangeCard(cardInPlay, coo[0], coo[1]);
            this.graveward.add(cardToReplace);
        } else if (choice == 1) {
            p.revealCard(coo[0], coo[1]);
            this.graveward.add(cardInPlay);
        }

        this.updatePlayerHand(p);
    }

    /**
     * Updates the player’s hand at the end of a turn:
     * - Checks for full columns where all cards are visible and have the same value.
     * - If such a column exists, marks it as “complete” (or removes it, depending on your rules).
     *
     * @param p the player whose hand is being updated
     */
    protected void updatePlayerHand(Player p) {
        boolean hasColumn;
        Card ref;
        Card card;
        for (int c = 0; c < p.getColumns(); c++) {
            hasColumn = true;
            ref = p.getCard(0, c);
            for (int l = 0; l < p.getRaws(); l++) {
                card = p.getCard(l, c);
                if (!card.isVisible()) {
                    hasColumn = false;
                } else if (ref.getValue() != card.getValue()) {
                    hasColumn = false;
                }
            }
            if (hasColumn) {
                p.hasColumn(c);
            }
        }
    }

    /**
     * Prints the current score list for all players to the console.
     * 
     * @return no return value
     */
    protected void printScore() {
        Player p;
        int score;
        System.out.println("Score : ");
        for (int i = 0; i < this.getNumberOfPlayer(); i++) {
            p = this.playerList.get(i);
            score = this.scoreList[i];
            System.out.println("\tPlayer " + p + "(" + i + ") : " + score);
        }
    }
}