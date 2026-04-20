package game;

import java.util.ArrayList;
public class Bot extends Player {

    /** The level of the bot 0 : easy 1 : medium 2 : hard */ //Not yet full implemented
    int level = 0;

    /**
     * Default constructor for the Bot class. Initializes the bot with default values and sets its name to "Bot".
     */
    public Bot() {
        super();
        this.setName("Bot");
    }

    /**
     * Constructor for the Bot class. Initializes the bot with the specified number of rows, columns, and difficulty level.
     * @param raw the number of rows in the bot's hand
     * @param column the number of columns in the bot's hand
     * @param level the difficulty level of the bot (0 for easy, 1 for medium, 2 for hard)
     */
    public Bot(int raw, int column, int level){
        super(false, raw, column);
        this.level = level;
        this.setName("Bot");
    }

    /**
     * Defines the bot's turn logic. This method should implement the decision-making process for the bot's actions during its turn.
     * The specific logic can be based on the bot's current hand, the game state, and any strategies you want the bot to follow.
     * For example, the bot might choose to draw a card, swap a card from its hand, or take any other action allowed by the game rules.
     * 
     * @param lib the library from which the bot can draw cards
     * @param graveward the list of cards in the graveyard that the bot can interact with
     */
    public void turn(Library lib, ArrayList<Card> graveward, boolean WantToMinimizeScore){
        Card card;
        System.out.println("Bot is taking its turn...");
        switch (this.level) {
            case 0:
                // Easy level logic
                graveward.add(lib.drawRandomCard(true));
                card = chooseRandomCard(true);
                card.reveal();
                break;
            case 1:
                // Medium level logic
                if(WantToMinimizeScore){
                    mediumIAMinimize(lib, graveward,2, true);
                } else {
                    mediumIAMaximaze(lib, graveward,2, true);
                }
                break;
            case 2:
                // Hard level logic
                if(WantToMinimizeScore){
                    mediumIAMinimize(lib, graveward,4, false);
                } else {
                    mediumIAMaximaze(lib, graveward,4, false);
                }
                break;
            default:
                System.err.println("Invalid bot level: " + this.level);
                break;
        }
    }

    /**
     * Returns the highest value card in the bot's hand that is visible. This method can be used as part of the bot's decision-making process to identify which card to swap.
     * 
     * @return an array containing the row, column, and value of the highest card that is visible, or null if all cards are visible
     */
    private int[] getHighestCard(){
        int max = Integer.MIN_VALUE;
        int column = -1;
        int raw = -1;
        Card card;
        for (int i = 0; i < this.raw; i++) {
            for (int j = 0; j < this.column; j++) {
                card = this.getCard(i, j);
                if(card == null){
                    continue;
                }
                if(card.isVisible() && card.getValue() > max){
                    max = card.getValue();
                    column = j;
                    raw = i;
                }
            }
        }
        return new int[]{raw, column, max};
    }

    /**
     * Returns the lowest value card in the bot's hand that is visible. This method can be used as part of the bot's decision-making process to identify which card to swap.
     * 
     * @return an array containing the row, column, and value of the lowest card that is visible, or null if all cards are visible
     */
    private int [] getLowestCard(){
        int min = Integer.MAX_VALUE;
        int column = -1;
        int raw = -1;
        Card card;
        for (int i = 0; i < this.raw; i++) {
            for (int j = 0; j < this.column; j++) {
                card = this.getCard(i, j);
                if(card == null){
                    continue;
                }
                if(card.isVisible() && card.getValue() < min){
                    min = card.getValue();
                    column = j;
                    raw = i;
                }
            }
        }
        return new int[]{raw, column, min};
    }

    /**
     * Chooses a random card from the bot's hand based on the visibility condition specified by the onlyReveal parameter.
     * 
     * @param onlyReveal a boolean indicating whether to choose only from hidden cards (true) or only from visible cards (false)
     * 
     * @return a randomly chosen card from the bot's hand that meets the visibility criteria
     */
    private Card chooseRandomCard(boolean onlyReveal){
        int column;
        int raw;
        Card card;
        do{
            column = (int) (Math.random() * this.column);
            raw = (int) (Math.random() * this.raw);
            card = this.getCard(raw, column);
        }while(card.isVisible() == onlyReveal);
        return card;
    }

    /**
     * Sets the bot's hand with the given 2D array of cards.
     * 
     * @param hand the 2D array of cards to set as the bot's hand
     */
    public void setHand(Card[][] hand) {
        this.hand.clear();
        for (int i = 0; i < hand.length; i++) {
            for (int j = 0; j < hand[i].length; j++) {
                this.hand.add(hand[i][j]);
            }
        }
    }

    /**
     * Checks if the bot has a column with all cards the same (except one card that can be different).
     * 
     * @return list of index all column with all cards the same (except one card that can be different), null if no such column exists
     */
    private ArrayList<Integer[]> hasAlmostColumn() {
        ArrayList<Integer[]> res = new ArrayList<>();
        for (int i = 0; i < this.column; i++) {
            int[] missingCard = getMissingCard(i);
            if(missingCard[0] != -666){
                res.add(new Integer[]{missingCard[0], missingCard[1]});
            }
        }
        return res;
    }

    /**
     * Returns the missing card in a column if there is a column with all cards the same (except one card that can be different).
     * 
     * @param column the index of the column to check for the missing card
     * 
     * @return an array containing the value and the index of the missing card, null if no such column exists or if the column index is invalid
     */
    private int [] getMissingCard(int column){
        ArrayList<Card> columnCards = getColumnCards(column);
        Card ref;
        Card card;
        int count = 0;
        for(int i = 0; i<columnCards.size();i++){
            ref = columnCards.get(i);
            if(!ref.isVisible()){
                continue;
            }

            count = 0;
            for(int j = i; j<columnCards.size();j++){
                card = columnCards.get(j);
                if(!card.isVisible()){
                    continue;
                }
                if(ref.isEqual(card)){
                    count++;
                }
            }

            if(count == columnCards.size() - 1 || count == columnCards.size()){
                return new int[]{ref.getValue(), column};
            }
        }
        int[] res = {-666,-666};
        return res;
    }

    /**
     * Returns a list of cards in the specified column.
     * 
     * @param column the index of the column to retrieve cards from
     * 
     * @return a list of cards in the specified column
     */
    private ArrayList<Card> getColumnCards(int column){
        ArrayList<Card> res = new ArrayList<>();
        for (int i = 0; i < this.raw; i++) {
            res.add(this.getCard(i, column));
        }
        return res;
    }

    /**
     * PURE DEBUG METHOD, NOT TO BE USED IN THE FINAL VERSION
     * Test method to check the functionality . It returns a string representation of the test.
     * 
     * @return a string representation of the test results
     */
    public String test(){
        ArrayList<Integer[]> res = hasAlmostColumn();
        if(res.isEmpty()) return "No column with all cards the same (except one card that can be different)";
        String result = "";
        for (int i = 0; i < res.size(); i++) {
            result += res.get(i)[1] + " ";
        }
        return result;
    }

    /**
     * 
     * This method implements the medium level AI logic for the bot's turn to reach minimum score. 
     * It takes into account the bot's current hand, the library, and the graveyard to make decisions about which cards to swap or reveal.
     * 
     * @param lib the library from which the bot can draw cards
     * @param graveward the list of cards in the graveyard that the bot can interact with
     * @param delta this variable delta is used to make the bot more or less aggressive in swapping cards. 
     * If the value of the card to take is less than the value of the card to drop minus delta, the bot will swap the cards. 
     * Otherwise, it will keep the card to drop and reveal a hidden card from its hand. 
     * Adjusting this value can make the bot more likely to swap cards (if delta is larger) or more likely to keep its current cards (if delta is smaller).
     * @param onlyReveal a boolean indicating if the bot knows the value of hidden cards from his hand.
     */
    private void mediumIAMinimize(Library lib, ArrayList<Card> graveward,int delta, boolean onlyReveal){
        Card card;
        int[] highestCard = getHighestCard();
        if(highestCard[2] > 0){
            Card card2Drop = this.getCard(highestCard[0], highestCard[1]);
            if(graveward.size() > 0){
                Card card2Take = graveward.get(graveward.size() - 1);
                if(card2Take.getValue() - delta < card2Drop.getValue()){
                    graveward.add(this.exchangeCard(card2Take, highestCard[0], highestCard[1]));
                    System.out.println("Bot took a card from the graveyard and exchanged it with a card from its hand.");
                } else {
                    card2Take = lib.drawRandomCard(true);
                    if(card2Take.getValue() - delta < card2Drop.getValue()){
                        graveward.add(this.exchangeCard(card2Take, highestCard[0], highestCard[1]));
                        System.out.println("Bot drew a card from the library and exchanged it with a card from its hand.");
                    } else {
                        graveward.add(card2Take);
                        card = this.chooseRandomCard(onlyReveal);
                        card.reveal();
                        System.out.println("Bot drew a card from the library but did not exchange it. Instead, it revealed a hidden card from its hand.");
                    }
                }
            } else {
                card = this.chooseRandomCard(onlyReveal);
                card.reveal();
            }
        } else {
            graveward.add(lib.drawRandomCard(true));
            card = this.chooseRandomCard(onlyReveal);
            card.reveal();
            System.out.println("Bot revealed a hidden card from its hand.");
        }
    }

    /**
     * This method implements the medium level AI logic for the bot's turn, with a focus on maximizing the bot's score. 
     * It takes into account the bot's current hand, the library, and the graveyard
     * @param lib the library from which the bot can draw cards
     * @param graveward the list of cards in the graveyard that the bot can interact with
     * @param delta this variable delta is used to make the bot more or less aggressive in swapping cards. 
     * If the value of the card to take is greater than the value of the card to drop plus delta, the bot will swap the cards. 
     * Otherwise, it will keep the card to drop and reveal a hidden card from its hand. 
     * Adjusting this value can make the bot more likely to swap cards (if delta is larger) or more likely to keep its current cards (if delta is smaller).
     * @param onlyReveal a boolean indicating if the bot knows the value of hidden cards from his hand.
     */
    private void mediumIAMaximaze(Library lib, ArrayList<Card> graveward,int delta, boolean onlyReveal){
        Card card;
        int[] lowersCard = getLowestCard();
        if(lowersCard[2] < 9){
            Card card2Drop = this.getCard(lowersCard[0], lowersCard[1]);
            if(graveward.size() > 0){
                Card card2Take = graveward.get(graveward.size() - 1);
                if(card2Take.getValue() - delta > card2Drop.getValue()){
                    graveward.add(this.exchangeCard(card2Take, lowersCard[0], lowersCard[1]));
                    System.out.println("Bot took a card from the graveyard and exchanged it with a card from its hand.");
                } else {
                    card2Take = lib.drawRandomCard(true);
                    if(card2Take.getValue() - delta > card2Drop.getValue()){
                        graveward.add(this.exchangeCard(card2Take, lowersCard[0], lowersCard[1]));
                        System.out.println("Bot drew a card from the library and exchanged it with a card from its hand.");
                    } else {
                        graveward.add(card2Take);
                        card = this.chooseRandomCard(onlyReveal);
                        card.reveal();
                        System.out.println("Bot drew a card from the library but did not exchange it. Instead, it revealed a hidden card from its hand.");
                    }
                }
            } else {
                card = this.chooseRandomCard(onlyReveal);
                card.reveal();
            }
        } else {
            graveward.add(lib.drawRandomCard(true));
            card = this.chooseRandomCard(onlyReveal);
            card.reveal();
            System.out.println("Bot revealed a hidden card from its hand.");
        }
    }
}
