package game;

import java.util.ArrayList;

public class Board {
    protected Library lib;
    protected ArrayList<Card> graveward; //Magic the GATHERING
    private int numberOfPlayer;
    protected ArrayList<Player> playerList;
    protected boolean isUiActive;

    public Board(){
        this.numberOfPlayer = 2;
        this.playerList = new ArrayList<Player>(this.numberOfPlayer);
        for(int i = 0;i<this.numberOfPlayer;i++){
            this.playerList.add(new Player(false, 3, 4));
        }
        this.graveward = new ArrayList<Card>();
        this.lib = new Library();
        this.isUiActive = false;
    }

    public Board(int NbPlayer, int line, int column, boolean[] listOfHuman){
        this.numberOfPlayer = NbPlayer;
        this.playerList = new ArrayList<Player>(this.numberOfPlayer);
        for(int i = 0;i<this.numberOfPlayer;i++){
            this.playerList.add(new Player(listOfHuman[i], line, column));
        }
        this.graveward = new ArrayList<Card>();
        this.lib = new Library();
        this.isUiActive = false;
    }

    public int getNumberOfPlayer(){
        return this.numberOfPlayer;
    }

    protected void drawBoardUi(){
        //PUT HOW TO DRAW THE UI HERE
    }

    protected void drawBoardWithoutUi(){
        System.out.print("Lib : #\t Grave : ");
        int gravewardSize = this.graveward.size();
        if( gravewardSize != 0){
            Card card = this.graveward.get(gravewardSize-1);
            System.out.println(card.getValue());
        } else {
            System.out.println("None");
        }
    }
}
