import game.gamerule.*;
import ui.MenuController;
import ui.MenuWindow;

public class Main {
    public static void main(String[] args) {
        // ancienne partie sans le GUI 
        /*
        boolean [] listOfPlayer = {false,true};
        Skyjo game = new Skyjo(3,4,listOfPlayer);
        game.game();
        */

        // partie avec le GUI
        MenuWindow menu = new MenuWindow();
        
        MenuController controller = new MenuController(menu);
        
        menu.setMenuListener(controller);
        
        System.out.println("Interface de lancement chargée test");
    }
}