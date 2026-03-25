import game.gamerule.InvertShortSkyjo;

public class Main{
    public static void main(String[] args) {
        //DRAW SETUP MENU HERE
        boolean [] list = {false,false};
        InvertShortSkyjo game = new InvertShortSkyjo(2,3,4,list);
        game.game();
        
    }
}