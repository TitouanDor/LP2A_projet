package game;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/**
 * Represents a player in the game, which can be either human or AI-controlled.
 * Each player has a hand of cards arranged in a grid (raw × column),
 * and can interact with the game via input or random decision-making.
 */
public class Player {

    /** True if the player is human, false if AI-controlled. */
    private boolean human;

    /** List of cards currently in the player's hand. */
    private ArrayList<Card> hand;

    /** Max number for rows */
    private int maxRaw;

    /**Max number of columns */
    private int maxColumn;

    /** Number of rows in the player's hand grid. */
    private int raw;

    /** Number of columns in the player's hand grid. */
    private int column;

    /** Random generator used for AI actions. */
    private Random rdm;

    /** Scanner used to read human player input. */
    private Scanner scanner;

    /** Name of the player. Defaults to "Default_name". */
    private String name;

    /**
     * Default constructor.
     * Initializes a non-human player with a 4×3 hand grid and a default name.
     */
    public Player() {
        this.human = false;
        this.maxRaw = 4;
        this.maxColumn = 3;
        this.hand = new ArrayList<Card>(this.raw * this.column);
        this.rdm = new Random();
        this.scanner = new Scanner(System.in);
        this.name = "Default_name";
    }

    /**
     * Constructs a player with specified type and hand dimensions.
     *
     * @param human  true if the player is human, false if AI-controlled
     * @param raw   number of rows in the player's hand grid
     * @param column number of columns in the player's hand grid
     */
    public Player(boolean human, int raw, int column) {
        this.human = human;
        this.maxRaw = raw;
        this.maxColumn = column;
        this.hand = new ArrayList<Card>(this.raw * this.column);
        this.rdm = new Random();
        this.scanner = new Scanner(System.in);
        this.name = "Default_name";
    }

    /**
     * Reset column and raw
     * 
     * @return no return value
     */
    public void resetRC(){
        this.column = this.maxColumn;
        this.raw = this.maxRaw;
    }

    /**
     * Give the number of column in the player's hand
     * 
     * @return (int) Number of raw 
     */
    public int getRaws(){
        return this.raw;
    }

    /**
     * Give the number of column in the player's hand
     * 
     * @return (int) Number of columns 
     */
    public int getColumns(){
        return this.column;
    }

    /**
     * Can reveal a card from player's hand
     * 
     * @param raw (int) raw of the selected card
     * @param column (int) column of the selected card
     * 
     * @return no return value
     */
    public void revealCard(int raw, int column){
        Card card = this.hand.get(raw*this.getColumns()+column);
        card.reveal();
    }

    /**
     * Give the selected card
     * 
     * @param raw (int) raw of the selected card
     * @param column (int) column of the selected card
     * 
     * @return (Card) selected card 
     */
    public Card getCard(int raw, int column){
        if (raw < this.getRaws() && column<this.getColumns()){
            return this.hand.get(raw*this.getColumns()+column);
        } else {
            return null;
        }
    }

    /**
     * Exchange a card from player's hand with an other card
     * 
     * @param new_card (Card) card to exchange with one of the player's hand
     * @param raw (int) raw of the player's hand card 
     * @param column (int) column of the player's hand card
     * 
     * @return (Card) player's hand card 
     * 
     * @exception raw no verification done here
     * @exception colum no verification done here
     */
    public Card exchangeCard(Card new_card, int raw, int column){
        int index = raw*this.getColumns()+column;
        Card card = this.hand.get(index);
        card.reveal();
        this.hand.set(index, new_card);
        return card;
    }

    /**
     * @return (boolean) true if the player if humain else false
     */
    public boolean isHumain(){
        return this.human;
    }

    /** 
     * Allow to set a given hand for the player
     * 
     * @param new_hand (Arraylist<Card>) the new hand to give to the player
     * 
     * @return no return value
    */
    public void setHand(ArrayList<Card> new_hand){
        this.hand = new_hand;
    }

    /**
     * @return (boolean) return true if all the player hand's card are revealed else false
     */
    public boolean isHandRevealed(){
        for (Card card : hand) {
            if (!card.isVisible()){
                return false;
            }
        }
        return true;
    }

    /**
     * Give the total value of the player's hand
     * 
     * @return (int) hand's value
     */
    public int getHandValue(){
        int score = 0;
        for (Card card : hand) {
            if(card.isVisible()){
                score += card.getValue();
            }
        }
        return score;
    }

    /**
     * Write in the console the player's hand
     * 
     * @return no return value
     */
    public void drawConsolHand(){
        Card card;
        for(int i = 0;i<this.getRaws();i++){
            for(int j=0;j<this.getColumns();j++){
                card = this.getCard(i, j);
                if (card.isVisible()){
                    System.out.print(card.getValue() + ";");
                } else {
                    System.out.print("#;");
                }
            }
            System.out.println();
        }
    }

    /**
     * Allow to reveal all the card form the player's hand
     * 
     * @return no return value
     */
    public void revealHand(){
        for (Card card : this.hand) {
            card.reveal();
        }
    }

    /**
     * Allow to choose a card from the player's hand
     * 
     * @param canBeNotVisible tell if the selected card has to be visible or not
     * 
     * @return (Card) the card selected
     */
    public Card chooseCardFromHand(boolean canBeNotVisible){
        Card card = null;
        int [] coo;
        do{
            coo = this.chooseXY();
            card = this.getCard(coo[0], coo[1]);
        }while(!canBeNotVisible && !card.isVisible());
        
        return card;
    }

    /** 
     * Allow humain layer to choose their card
     * 
     * @return (int [raw, column]) coordinat of the selected card 
    */
    public int[] chooseXY(){
        int [] coo = new int[2];
        if (this.isHumain()){
            do {
                System.out.println("Enter raw : ");
                coo[0] = this.scanner.nextInt();
            }while(coo[0]>=this.getRaws());

            do{
                System.out.println("Enter column : ");
                coo[1] = this.scanner.nextInt();
            }while(coo[1]>=this.getColumns());
        } else {
            coo[0] = rdm.nextInt(this.getRaws());
            coo[1] = rdm.nextInt(this.getColumns());
        }
        return coo;
    }

    /**
     * Allow the player to choose between 2 options
     * 
     * @param text (String) text to print to tell the choice
     * 
     * @return (int) 0 or 1
     */
    public int chooseBetweenTwo(String text){
        int c = -1;
        int numberOfChoice = 2;
        if(this.isHumain()){
            do{
                System.out.println(text);
                c = this.scanner.nextInt();
            }while (c<=0 && c>numberOfChoice);
        } else {
            c = this.rdm.nextInt(numberOfChoice);
        }
        return c;
    }

    /**
     * Allow to delete a whole column
     * 
     * @param column_index (int) the column number
     * 
     * @return no return value
     * 
     * @exception colum_index no verification done here
     */
    public void hasColumn(int column_index){
        for (int i = 0;i<this.getRaws();i++){
            int index = i*this.getColumns()+column_index-i;
            this.hand.remove(index);
        }
        this.column -= 1;
    }

    public String toString(){
        return this.name;
    }
}
