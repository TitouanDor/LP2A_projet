package game;

import java.util.ArrayList;

public class Board {
    protected Library lib;
    protected ArrayList<Card> graveward; //Magic the GATHERING
    private int numberOfPlayer;
    protected ArrayList<Player> playerList;

    public Board(){
        this.numberOfPlayer = 2;
        this.playerList = new ArrayList<Player>(this.numberOfPlayer);
        for(int i = 0;i<this.numberOfPlayer;i++){
            this.playerList.add(new Player(true, 3, 4));
        }
        this.graveward = new ArrayList<Card>();
        this.lib = new Library();
    }

    public Board(int NbPlayer, int line, int column){
        this.numberOfPlayer = NbPlayer;
        this.playerList = new ArrayList<Player>(this.numberOfPlayer);
        for(int i = 0;i<this.numberOfPlayer;i++){
            this.playerList.add(new Player(true, line, column));
        }
        this.graveward = new ArrayList<Card>();
        this.lib = new Library();
    }
}
