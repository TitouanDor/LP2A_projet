import game.gamerule.InvertSkyjo;

public class Main{
    public static void main(String[] args) {
        //DRAW SETUP MENU HERE
        boolean [] listOfPlayer = {false,false,false};
        InvertSkyjo game = new InvertSkyjo(3,4,listOfPlayer);
        game.game();
        
    }
}