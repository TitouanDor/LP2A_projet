package game.gamerule;

/*
* we add an enumeration to know where we are in the turn, 
* and a variable to store the card that the player has just drawn.
*/
public enum GameStep {
    START_TURN,       // Waiting for a draw (Deck or Grave)
    CARD_PICKED,      // The player has a card "in hand," is waiting to choose a place on his board.
    WAITING_FOR_REVEAL,    //Waits for the player to return a card 
    GAME_OVER,
}