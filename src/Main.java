import game.gamerule.ShortSkyjo;

public class Main{
    public static void main(String[] args) {
        //DRAW SETUP MENU HERE
        boolean [] list = {false,false};
        ShortSkyjo game = new ShortSkyjo(2,3,4,list);
        game.game();
        
    }
}