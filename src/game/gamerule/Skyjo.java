package game.gamerule;
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

    private void setUpGame(){
        int nbCard = 0;
        for (Player player : this.playerList) {
            nbCard = player.getColumn()*player.getLine();
            player.setHand(lib.drawSetUp(nbCard));
            if (player.isHumain()){
                //ASK SOMETHING TO REVEAL 2 CARD
            } else {
                //DO SOMETHING TO REVEAL 2 CARD
            }
        }
    }

    private boolean isFinish(){
        for (Player p : this.playerList) {
            if (p.isHandRevealed()){
                return true;
            }
        }
        return false;
    }

    public void game(){
        this.setUpGame();
        Player playingPlayer;
        int i = 0;
        while (!this.isFinish()){
            if (i>=this.getNumberOfPlayer()){
                i = 0;
            } 
            playingPlayer = this.playerList.get(i);
            if(this.isUiActive){

            } else {
                this.drawWithoutUI(playingPlayer);
            }
            
            this.playerTurn(playingPlayer);
            i++;
        }
    }

    private void playerTurn(Player p){
        int line = 0;
        int column = 0;
        Card card;
        if (p.isHumain()){
            //DO HUMAIN TURN
        } else {
            do{
                line = rdm.nextInt(p.getLine());
                column = rdm.nextInt(p.getColumn());
                System.out.println("Coo : " + line + ";" + column);
                card = p.getCard(line, column);
            }while(card == null || card.isVisible());
            card.reveal();
        }
    }

    private void drawWithoutUI(Player p){
        Card card;
        for(int i = 0;i<p.getLine();i++){
            for(int j=0;j<p.getColumn();j++){
                card = p.getCard(i, j);
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