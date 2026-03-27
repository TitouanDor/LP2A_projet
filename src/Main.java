import game.gamerule.*;

public class Main{
    public static void main(String[] args) {
        //DRAW SETUP MENU HERE
        boolean [] listOfPlayer = {false,true};
        Skyjo game = new Skyjo(3,4,listOfPlayer);
        game.game();
        
    }
}