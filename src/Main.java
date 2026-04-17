import game.gamerule.*;
import ui.MenuController;
import ui.MenuWindow;

public class Main {
    public static void main(String[] args) {
        // ancienne partie sans le GUI 
        if(args.length > 0){
            if(args[0].equals("consol"))
            System.out.println("Starting game without GUI...");
            boolean [] listOfPlayer = {false,true};
            Skyjo game = new Skyjo(3,4,listOfPlayer);
            game.game();
        } else {
            MenuWindow menu = new MenuWindow();
            MenuController controller = new MenuController(menu);
            menu.setMenuListener(controller);
            System.out.println("Interface de lancement chargée test");
        }
        
    }
}