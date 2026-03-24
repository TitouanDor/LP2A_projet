package game;
import java.util.ArrayList;


public class Player {
    private boolean human;
    private ArrayList<Card> hand;
    private int line;
    private int column;

    public Player(){
        this.human = false;
        this.line = 4;
        this.column = 3;
        this.hand = new ArrayList<Card>(this.line*this.column);
    }

    public Player(boolean human, int line, int column){
        this.human = human;
        this.line = line;
        this.column = column;
        this.hand = new ArrayList<Card>(this.line*this.column);
    }

    public int getLine(){
        return this.line;
    }

    public int getColumn(){
        return this.column;
    }

    public void revealCard(int line, int column){
        Card card = this.hand.get(line*this.getColumn()+column);
        card.reveal();
    }

    public Card getCard(int line, int column){
        if (line < this.getLine() && column<this.getColumn()){
            return this.hand.get(line*this.getColumn()+column);
        } else {
            return null;
        }
    }

    public Card exchangeCard(Card new_card, int line, int column){
        int index = line*this.getColumn()+column;
        Card card = this.hand.get(index);
        card.reveal();
        this.hand.set(index, new_card);
        return card;
    }

    public boolean isHumain(){
        return this.human;
    }

    public void setHand(ArrayList<Card> new_hand){
        this.hand = new_hand;
    }

    public boolean isHandRevealed(){
        for (Card card : hand) {
            if (!card.isVisible()){
                return false;
            }
        }
        return true;
    }
}
