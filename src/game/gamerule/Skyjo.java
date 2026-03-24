package game.gamerule;
import java.util.ArrayList;
import java.util.Random;

import game.Board;
import game.Card;

public class Skyjo extends Board{
    private boolean hasIA;
    private boolean mainUserTurn;
    private boolean isUiActive;
    private Random rdm;


    public Skyjo(){
        super(3,4);
        this.hasIA = true;
        rdm = new Random();
    }

    public Skyjo(boolean hasRobot){
        super(3,4);
        this.hasIA = hasRobot;
        rdm = new Random();
    }

    protected boolean isFinish(){
        boolean end1 = true;
        boolean end2 = true;
        for (Card card : player1) {
            if(!card.isVisible()){
                end1 = false;
            }
        }

        for (Card card : player2) {
            if(!card.isVisible()){
                end2 = false;
            }
        }
        return end2 || end1;
    }

    protected void setup(){
        this.player1 = this.lib.drawSetUp(getColumn()*getLine());
        this.player2 = this.lib.drawSetUp(getColumn()*getLine());
    }

    protected int getScore(boolean isMainUser){
        int score = 0;
        if (isMainUser){
            for (Card card : this.player1) {
                score = score + card.getValue();
            }
        } else {
            for (Card card : this.player2) {
                score = score + card.getValue();
            }
        }
        return score;
    }

    private void IATurn(){
        Card card;
        int line;
        int column;
        do {
            line = rdm.nextInt(this.getLine());
            column = rdm.nextInt(this.getColumn());
            System.out.println("Coo : " + line + ";" + column);
            card = this.player2.get(line*this.getColumn()+column);
        }while(card.isVisible());
        
        card.reveal();
    }

    private void humainTurn(){

        ArrayList<Card> list;
        if (mainUserTurn){
            list = this.player1;
        } else {
            list = this.player2;
        }


        if(!this.isUiActive){
            Card card;
            for(int i = 0;i<getLine();i++){
                for(int j=0;j<getColumn();j++){
                    card = list.get(i*getColumn()+j);
                    if (card.isVisible()){
                        System.out.print(card.getValue() + ";");
                    } else {
                        System.out.print("#;");
                    }
                }
                System.out.println();
            }

            
        }
    }


    public void game(){
        this.setup();
        this.isUiActive = false;
        this.mainUserTurn = true;
        while (!this.isFinish()) {
            if(mainUserTurn){
                System.out.println("Main user turn");
                this.humainTurn();

            } else if (!this.hasIA){
                System.out.println("Second player turn");
                this.humainTurn();

            } else {
                System.out.println("IA turn");
                IATurn();
            }

            this.mainUserTurn = !this.mainUserTurn;
        }
        System.out.println(this.getScore(true) + " vs " + this.getScore(false));
    }
}
