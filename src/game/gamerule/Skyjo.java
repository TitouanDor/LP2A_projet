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

    /** ID of the player who first reveals all cards in the current round. */
    protected int id_finisher;

    /** State variable to track the current step of the turn */
    protected GameStep currentStep = GameStep.START_TURN;

    /** Temporary variable to hold the card that the player has just drawn. */
    protected Card cardInHand = null;

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
    public Skyjo(int line, int column, boolean[] listOfHuman) {
        super(listOfHuman.length, line, column, listOfHuman);
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
    public void setUpRound() {
        //security 
        if (this.lib == null || this.lib.getCardNumber() == 0) {
            System.err.println("CRITICAL ERROR: The library is empty or not loaded");
            System.err.println("Check that the card.dat file is in the right place.");
            return; // Stop here to prevent further errors when trying to draw cards
        }
        int nbCard = 0;
        this.lib.reset(); // Reset the library to its initial state at the start of each round
        for (Player player : this.playerList) {
            player.resetRC();
            nbCard = player.getColumns() * player.getRaws();
            player.setHand(lib.drawSetUp(nbCard));
            if(!this.isUiActive) {
                Card card;
                for (int i = 0; i < 2; i++) {
                    do {
                        card = player.chooseCardFromHand(true);
                    } while (card.isVisible());
                    card.reveal();
                }
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

        if (this.isUiActive) {
            System.out.println("UI Mode: Initialization complete, switching to display.");
            return; 
        }

        // The code below will ONLY run if you launch the game in text mode
        Player playingPlayer;
        int i = 0;
        while (!this.isRoundFinish()) {
            if (i >= this.getNumberOfPlayer()) {
                i = 0;
            }
            playingPlayer = this.playerList.get(i);
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
            System.out.println("Play better, your score is multiplied by 2" + " (finisher score : " + tempScore[id_finisher] + " vs min score without winner : " + scoreMinWithoutWinner + ")");
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

        if(p.isHumain()){
            p.drawConsolHand();
            this.drawBoardWithoutUi();
        }    
    
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
        boolean isComplete;
        Card firstCard;
        Card currentCard;
        for (int c = 0; c < p.getColumns(); c++) {
            isComplete = true;
            firstCard = p.getCard(0, c);
        
            if (firstCard == null) continue; 

            for (int l = 0; l < p.getRaws(); l++) {
                currentCard = p.getCard(l, c);
            
                if (currentCard == null || !currentCard.isVisible() || currentCard.getValue() != firstCard.getValue()) {
                    isComplete = false;
                    break;
                }
            }

            if (isComplete) {
                Card toDiscard;
                // ACTION: We empty the column in the data
                for (int l = 0; l < p.getRaws(); l++) {
                    toDiscard = p.getCard(l, c);
                    this.graveward.add(toDiscard); // Adding to the discard pile
                    p.setCard(l, c, null);         
                }
                System.out.println("Column " + c + " dropped " + p.toString());
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

    // -------VERSION UI---------//

    /**
     * Manages the actions of the human player via the UI.
     * 
     * @param row the row index of the clicked card on the player's hand grid
     * @param col the column index of the clicked card on the player's hand grid
     * @param isDeck true if the player clicked on the deck, false otherwise
     * @param isGrave true if the player clicked on the graveyard, false otherwise
     */
    public void handleAction(int row, int col, boolean isDeck, boolean isGrave) {
        if (this.currentStep == GameStep.GAME_OVER) return;

        switch (this.currentStep) {
            case START_TURN:
                if (isDeck) {
                    this.cardInHand = this.lib.drawRandomCard(true); 
                    this.currentStep = GameStep.CARD_PICKED;
                    System.out.println("Drawn from the deck : " + cardInHand.getValue());
                } else if (isGrave && !graveward.isEmpty()) {
                    this.cardInHand = this.graveward.remove(this.graveward.size() - 1);
                    this.currentStep = GameStep.CARD_PICKED;
                    System.out.println("Draw from the discard pile : " + cardInHand.getValue());
                }
                break;

            case CARD_PICKED:
                if (isGrave) {
                    // the player doesn’t want the drawn card 
                    this.graveward.add(this.cardInHand); // go in the discard 
                    this.cardInHand = null;
                    this.currentStep = GameStep.WAITING_FOR_REVEAL;
                    System.out.println("Card rejected ! Choose a card to reveal.");
                } else if (row != -1 && col != -1) {
                    // classic excahnge 
                    Player p = this.getCurrentPlayer();
                    Card oldCard = p.exchangeCard(this.cardInHand, row, col);
                    this.graveward.add(oldCard);
                    this.cardInHand = null;
                    advanceTurn();
                }
                break;

            case WAITING_FOR_REVEAL:
                if (row != -1 && col != -1) {
                    Player p = this.getCurrentPlayer();
                    Card toReveal = p.getCard(row, col);
                    if (!toReveal.isVisible()) {
                        toReveal.reveal();
                        advanceTurn();
                    }
                }
                break;
        }
    }

    /**
     * Advances the game to the next turn.
     */
    protected void advanceTurn() {
        Player p = this.getCurrentPlayer();
        this.updatePlayerHand(p);

        // check if it is the end of the game 
        if (this.isRoundFinish()) {
            this.updateScore();
            if (this.isGameFinish()) {
                this.currentStep = GameStep.GAME_OVER;
                this.endGame();
            } else {
                this.setUpRound(); // new round 
                this.currentStep = GameStep.START_TURN;
            }
        } else {
            // next player
            
            this.id_player = (this.id_player + 1) % this.getNumberOfPlayer();
            this.currentStep = GameStep.START_TURN;

            // If it’s an AI, you automate your turn.
            if (!this.getCurrentPlayer().isHumain()) {
                playAiTurn();
            }
        }
    }

    /**
     * Plays the turn for an AI player. 
     */
    protected void playAiTurn() {
        System.out.println("AI thinking...");
        // Must be improved with a real strategy, but for now it just draws from the deck and exchanges with the first card of its hand
        Player p = getCurrentPlayer();
        Card c = this.lib.drawRandomCard(true);
        this.graveward.add(p.exchangeCard(c, 0, 0)); 
        advanceTurn();
    }

    /**
     * Getter for the current step of the turn, used by the UI to determine what actions are available to the player.
     * 
     * @return the current step of the turn
     */ 
    public GameStep getStep() { 
        return this.currentStep; 
    }

    /**
     * Getter for the card currently in the player's hand (the card that was just drawn and is waiting to be exchanged or rejected).
     * 
     * @return the card in the player's hand
     */
    public Card getCardInHand() {
        return this.cardInHand; 
    }

}