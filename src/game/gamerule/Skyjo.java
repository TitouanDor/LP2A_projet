package game.gamerule;
import java.util.ArrayList;
import java.util.Random;

import game.Board;
import game.Card;
import game.Player;

public class Skyjo extends Board{
    private boolean isUiActive;
    private Random rdm;

    public Skyjo(){
        super();
        this.rdm = new Random();
        this.isUiActive = false;
    }

    public Skyjo(int NbPlayer, int line, int column){
        super(NbPlayer, line, column);
        this.rdm = new Random();
        this.isUiActive = false;
    }

    public void game(){

    }

    private void playerTurn(Player p){

    }
}
