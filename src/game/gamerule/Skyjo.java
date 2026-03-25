package game.gamerule;

import game.Board;
import game.Card;
import game.Player;

public class Skyjo extends Board{
    private int [] scoreList;

    public Skyjo(){
        super();
        this.scoreList = new int[this.getNumberOfPlayer()];
    }

    public Skyjo(int NbPlayer, int line, int column, boolean[] listOfHuman){
        super(NbPlayer, line, column, listOfHuman);
        this.scoreList = new int[this.getNumberOfPlayer()];
    }

    private void setUpGame(){
        int nbCard = 0;
        for (Player player : this.playerList) {
            nbCard = player.getColumn()*player.getLine();
            player.setHand(lib.drawSetUp(nbCard));
            Card card;
            for(int i = 0;i<2;i++){
                card = player.chooseCardFromHand();
                card.reveal();
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
        if(!this.isUiActive){
            System.out.println("Start the game");
        }
        while (!this.isFinish()){
            if (i>=this.getNumberOfPlayer()){
                i = 0;
            } 
            playingPlayer = this.playerList.get(i);
            if(this.isUiActive){
                this.drawBoardUi();

            } else if (playingPlayer.isHumain()){
                playingPlayer.drawConsolHand();
            }
            
            playingPlayer.playerTrun();
            i++;
        }

        this.endGame();
    }

    private void endGame(){
        Player p;
        for(int i = 0;i<this.getNumberOfPlayer();i++){
            p = this.playerList.get(i);
            p.revealHand();
            this.scoreList[i] = p.getHandValue();
            System.out.println("Score " + i + " : " + this.scoreList[i]);
            p.drawConsolHand();
        }
    }
}