import game.gamerule.*;
import ui.MenuController;
import ui.MenuWindow;


/**
 * The entry point of the UTBM Skyjo application.
 * This class handles the initial startup logic, allowing the game to run 
 * either in command-line console mode or with a Graphical User Interface (GUI).
 */
public class Main {
    /**
     * Main method that launches the game.
     * * @param args Command-line arguments. 
     * If the first argument is "consol", the game starts in text mode.
     * Otherwise, it initializes the MenuWindow and MenuController for the GUI mode.
     */
    public static void main(String[] args) {
        // part without UI  
        if(args.length > 0){
            if(args[0].equals("consol"))
            System.out.println("Starting game without GUI...");
            boolean [] listOfPlayer = {false,true};
            Skyjo game = new Skyjo(3,4,listOfPlayer, 1);
            game.game();
        } else {
            //part with UI
            MenuWindow menu = new MenuWindow();
            MenuController controller = new MenuController(menu);
            menu.setMenuListener(controller);
            System.out.println("launch with the GUI interface ");
        }
    }
}