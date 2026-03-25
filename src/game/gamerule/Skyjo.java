package game.gamerule;

import game.Board;
import game.Card;
import game.Player;

public class Skyjo extends Board{
    private int [] scoreList;
    private int id_finisher;

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
                do {
                    card = player.chooseCardFromHand(true);
                }while(card.isVisible());
                card.reveal();
            }
        }
        this.graveward.add(this.lib.drawRandomCard(true));
    }

    private boolean isRoundFinish(){
        Player p;
        for(int i = 0;i<this.getNumberOfPlayer();i++){
            p = this.playerList.get(i);
            if(p.isHandRevealed()){
                this.id_finisher = i;
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
        while (!this.isRoundFinish()){
            if (i>=this.getNumberOfPlayer()){
                i = 0;
            } 
            playingPlayer = this.playerList.get(i);
            if(this.isUiActive){
                this.drawBoardUi();

            } else if (playingPlayer.isHumain()){
                System.out.println("Player : " + i + " your turn !!");
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
            this.printScore();
        }
        this.endGame();

    }

    private void updateScore(){
        Player p;
        int [] tempScore = new int[this.getNumberOfPlayer()];
        int scoreMinWithoutWinner;
        if(id_finisher == 0){
            scoreMinWithoutWinner = this.playerList.get(1).getHandValue();
        } else {
            scoreMinWithoutWinner = this.playerList.get(0).getHandValue();
        }

        for(int i = 0;i<this.getNumberOfPlayer();i++){
            p = this.playerList.get(i);
            p.revealHand();
            tempScore[i] = p.getHandValue();
            if(i != id_finisher && tempScore[i]<scoreMinWithoutWinner){
                scoreMinWithoutWinner = tempScore[i];
            }
        }

        if(scoreMinWithoutWinner<=tempScore[id_finisher]){
            System.out.println("Play better youre score is multiply per 2");
            tempScore[id_finisher] *= 2;
        }

        for(int i = 0;i<this.getNumberOfPlayer();i++){
            this.scoreList[i] += tempScore[i];
        }
    }

    private void endGame(){
        int id_winner = 0;
        for(int i = 0; i<this.getNumberOfPlayer();i++){
            if(this.scoreList[id_winner] > this.scoreList[i]){
                id_winner = i;
            }
        }
        Player p = this.playerList.get(id_winner);

        System.out.println("THE WINNER IS : " + p + " WITH A SCORE OF : " + this.scoreList[id_winner]);
    }

    private void playerTrun(Player p){
        int choice;
        Card cardInPlay;
        int [] coo;
        Card cardToReplace;

        choice = p.chooseBetweenTwo("Do you want to draw the top Card of : \n\t(0) : the library\n\t(1) : the graveward");
        if (choice == 0){
            cardInPlay = this.lib.drawRandomCard(true);
            if(p.isHumain()){
                System.out.println(cardInPlay.getValue());
            }
        } else if (choice == 1){
            int index = this.graveward.size()-1;
            cardInPlay = this.graveward.get(index);
            this.graveward.remove(index);
            cardInPlay.reveal();
        } else {
            cardInPlay = null;
        }
        choice = p.chooseBetweenTwo("Do you want to : \n\t(0) : exchange the card\n\t(1) : reveal one of your hand/board");
        coo = p.chooseXY();
        if (choice == 0){
            cardToReplace = p.exchangeCard(cardInPlay, coo[0], coo[1]);
            this.graveward.add(cardToReplace);
        } else if(choice == 1){
            p.revealCard(coo[0], coo[1]);
            this.graveward.add(cardInPlay);
        }
        
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

    private void printScore(){
        Player p;
        int score;
        System.out.println("Score : ");
        for(int i = 0;i<this.getNumberOfPlayer();i++){
            p = this.playerList.get(i);
            score = this.scoreList[i];
            System.out.println("\tPlayer " + p + "(" + i + ") : " + score);
        }
    }
}