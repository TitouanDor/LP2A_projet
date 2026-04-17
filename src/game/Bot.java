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
     */
    public void turn(){
        int column;
        int raw;
        Card card;
        switch (this.level) {
            case 0:
                // Easy level logic
                do{
                    column = (int) (Math.random() * this.column);
                    raw = (int) (Math.random() * this.raw);
                    card = this.getCard(raw, column);
                }while(card.isVisible());
                card.reveal();
                break;
            case 1:
                // Medium level logic
                break;
            case 2:
                // Hard level logic
                break;
            default:
                // Default logic
                break;
        }
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

    private ArrayList<Card> getColumnCards(int column){
        ArrayList<Card> res = new ArrayList<>();
        for (int i = 0; i < this.raw; i++) {
            res.add(this.getCard(i, column));
        }
        return res;
    }

    public String test(){
        ArrayList<Integer[]> res = hasAlmostColumn();
        if(res.isEmpty()) return "No column with all cards the same (except one card that can be different)";
        String result = "";
        for (int i = 0; i < res.size(); i++) {
            result += res.get(i)[1] + " ";
        }
        return result;
    }
}
