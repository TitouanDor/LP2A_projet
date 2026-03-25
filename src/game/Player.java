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
    private String name;

    public Player(){
        this.human = false;
        this.line = 4;
        this.column = 3;
        this.hand = new ArrayList<Card>(this.line*this.column);
        this.rdm = new Random();
        this.scanner = new Scanner(System.in);
        this.name = "Default_name";
    }

    public Player(boolean human, int line, int column){
        this.human = human;
        this.line = line;
        this.column = column;
        this.hand = new ArrayList<Card>(this.line*this.column);
        this.rdm = new Random();
        this.scanner = new Scanner(System.in);
        this.name = "Default_name";
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

    public Card chooseCardFromHand(boolean canBeNotVisible){
        Card card = null;
        int [] coo;
        do{
            coo = this.chooseXY();
            card = this.getCard(coo[0], coo[1]);
        }while(!canBeNotVisible && !card.isVisible());
        
        return card;
    }

    public int[] chooseXY(){
        int [] coo = {-1,-1};
        if (this.isHumain()){
            do {
                System.out.println("Enter line : ");
                coo[0] = this.scanner.nextInt();
            }while(coo[0]>=this.getLine());

            do{
                System.out.println("Enter column : ");
                coo[1] = this.scanner.nextInt();
            }while(coo[1]>=this.getColumn());
        } else {
            coo[0] = rdm.nextInt(this.getLine());
            coo[1] = rdm.nextInt(this.getColumn());
        }
        return coo;
    }

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

    public void hasColumn(int column_index){
        for (int i = 0;i<this.getLine();i++){
            int index = i*this.getColumn()+column_index-i;
            this.hand.remove(index);
        }
        this.column -= 1;
    }

    public String toString(){
        return this.name;
    }
}
