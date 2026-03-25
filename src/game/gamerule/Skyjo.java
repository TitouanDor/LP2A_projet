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

    private void setUpRound(){
        int nbCard = 0;
        for (Player player : this.playerList) {
            nbCard = player.getColumn()*player.getLine();
            player.setHand(lib.drawSetUp(nbCard));
            Card card;
            for(int i = 0;i<2;i++){
                card = player.chooseCardFromHand(true);
                card.reveal();
            }
        }
        this.graveward.add(this.lib.drawRandomCard(true));
    }

    private boolean isRoundFinish(){
        for (Player p : this.playerList) {
            if (p.isHandRevealed()){
                return true;
            }
        }
        return false;
    }

    private boolean isGameFinish(){
        for (int score : this.scoreList) {
            if(score>=100){
                return true;
            }
        }
        return false;
    }

    private void round(){
        this.setUpRound();
        Player playingPlayer;
        int i = 0;
        if(!this.isUiActive){
            System.out.println("Start the game");
        }
        while (!this.isRoundFinish()){
            if (i>=this.getNumberOfPlayer()){
                i = 0;
            } 
            playingPlayer = this.playerList.get(i);
            if(this.isUiActive){
                this.drawBoardUi();

            } else if (playingPlayer.isHumain()){
                this.drawBoardWithoutUi();
                playingPlayer.drawConsolHand();
            }
            
            this.playerTrun(playingPlayer);
            i++;
        }
    }

    public void game(){
        for(int i = 0;i<this.getNumberOfPlayer();i++){
            this.scoreList[i] = 0;
        }
        while(!isGameFinish()){
            this.round();
            this.updateScore();
        }
        this.endGame();

    }

    private void updateScore(){
        Player p;
        for(int i = 0;i<this.getNumberOfPlayer();i++){
            p = this.playerList.get(i);
            p.revealHand();
            this.scoreList[i] += p.getHandValue();
        }
    }

    private void endGame(){
        Player p;
        for(int i = 0;i<this.getNumberOfPlayer();i++){
            p = this.playerList.get(i);
            p.revealHand();
            this.scoreList[i] = p.getHandValue();
            System.out.println("Score " + i + " : " + this.scoreList[i]);
        }
    }

    private void playerTrun(Player p){
        int placeToDraw = p.chooseLibOrGrave();
        Card cardInPlay;
        int [] coo;
        Card cardToReplace;
        if (placeToDraw == 0){
            cardInPlay = this.lib.drawRandomCard(true);
            System.out.println(cardInPlay.getValue());
        } else if (placeToDraw == 1){
            int index = this.graveward.size()-1;
            cardInPlay = this.graveward.get(index);
            this.graveward.remove(index);
            cardInPlay.reveal();
        } else {
            cardInPlay = null;
        }

        coo = p.chooseXY();
        cardToReplace = p.exchangeCard(cardInPlay, coo[0], coo[1]);
        this.graveward.add(cardToReplace);
        this.updatePlayerHand(p);
    }

    private void updatePlayerHand(Player p){
        boolean hasColumn;
        Card ref;
        Card card;
        for(int c = 0;c<p.getColumn();c++){
            hasColumn = true;
            ref = p.getCard(0, c);
            for(int l = 0;l<p.getLine();l++){
                card = p.getCard(l, c);
                if (!card.isVisible()){
                    hasColumn = false;
                } else if(ref.getValue()!= card.getValue()){
                    hasColumn = false;
                }
            }
            if(hasColumn){
                p.hasColumn(c);
            }
        }
    }

}