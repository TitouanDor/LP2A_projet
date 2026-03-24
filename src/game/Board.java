package game;

import java.util.ArrayList;

public class Board {
    protected Library lib;
    protected ArrayList<Card> player1;
    protected ArrayList<Card> player2;
    protected ArrayList<Card> graveward; //Magic the GATHERING
    private int column;
    private int line;

    public Board(){
        this.column = 3;
        this.line = 4;
        this.player1 = new ArrayList<Card>(this.column*this.line);
        this.player2 = new ArrayList<Card>(this.column*this.line);
        this.graveward = new ArrayList<Card>();
        this.lib = new Library();
    }

    public Board(int line, int column){
        this.column = column;
        this.line = line;
        this.graveward = new ArrayList<Card>();
        this.lib = new Library();
        this.player1 = new ArrayList<Card>(this.column*this.line);
        this.player2 = new ArrayList<Card>(this.column*this.line);
    }

    public void revelCard(boolean isMainUser, int line, int column){
        Card card;
        if(isMainUser){
            card = this.player1.get(line*this.column + column);
        } else {
            card = this.player2.get(line*this.column + column);
        }
        card.reveal();
    }

    public Card getCardBoard(boolean isMainUser, int line, int column){
        if(isMainUser){
            return this.player1.get(line*this.column + column);
        } 
        return this.player2.get(line*this.column + column);
    }

    public void setCardBoard(boolean isMainUser, int line, int column, Card card){
        if(isMainUser){
            this.player1.set(line*this.column + column, card);
        } 
        this.player2.set(line*this.column + column, card);
    }

    public int getLine(){
        return this.line;
    }

    public int getColumn(){
        return this.column;
    }
}
