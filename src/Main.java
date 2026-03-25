import game.gamerule.Skyjo;

public class Main{
    public static void main(String[] args) {
        //DRAW SETUP MENU HERE
        boolean [] list = {true,false};
        Skyjo game = new Skyjo(2,3,4,list);
        game.game();
        
    }
}