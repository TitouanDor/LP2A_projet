package game;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Player {
    private boolean human;
    private ArrayList<Card> hand;
    private int line;
    private int column;
    private Random rdm;
    private Scanner scanner;

    public Player(){
        this.human = false;
        this.line = 4;
        this.column = 3;
        this.hand = new ArrayList<Card>(this.line*this.column);
        this.rdm = new Random();
        this.scanner = new Scanner(System.in);
    }

    public Player(boolean human, int line, int column){
        this.human = human;
        this.line = line;
        this.column = column;
        this.hand = new ArrayList<Card>(this.line*this.column);
        this.rdm = new Random();
        this.scanner = new Scanner(System.in);
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

    public int getHandValue(){
        int score = 0;
        for (Card card : hand) {
            if(card.isVisible()){
                score += card.getValue();
            }
        }
        return score;
    }

    public void drawConsolHand(){
        Card card;
        for(int i = 0;i<this.getLine();i++){
            for(int j=0;j<this.getColumn();j++){
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

    public void revealHand(){
        for (Card card : this.hand) {
            card.reveal();
        }
    }

    public Card chooseCardFromHand(){
        Card card = null;
        int choosenLine = this.getLine();
        int choosenColumn = this.getColumn();
        if (this.isHumain()){
            //ASK FOR A CARD FROM HIS HAND
            do {
                System.out.println("Enter line : ");
                choosenLine = this.scanner.nextInt();
            }while(choosenLine>=this.getLine());

            do{
                System.out.println("Enter column : ");
                choosenColumn = this.scanner.nextInt();
            }while(choosenColumn>=this.getColumn());
            card = this.getCard(choosenLine, choosenColumn);
        } else {
            do{
                choosenLine = rdm.nextInt(this.getLine());
                choosenColumn = rdm.nextInt(this.getColumn());
                //System.out.println("Coo : " + choosenLine + ";" + choosenColumn);
                card = this.getCard(choosenLine, choosenColumn);
            }while(card == null || card.isVisible());
        }
        return card;
    }

    public void playerTrun(){
        Card card = chooseCardFromHand();
        card.reveal();
    }

}
