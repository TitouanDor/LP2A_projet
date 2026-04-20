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

        /*Card [][] hand = {
            {new Card(1, "1", "blue"), new Card(2,"2", "blue"), new Card(3, "3", "blue"), new Card(4, "4", "blue")},
            {new Card(9, "9", "blue"), new Card(6, "6", "blue"), new Card(7, "7", "blue"), new Card(4, "4", "blue")},
            {new Card(9, "9", "blue"), new Card(2, "2", "blue"), new Card(11, "11", "blue"), new Card(12, "12", "blue")}
        };

        Bot bot = new Bot(hand.length, hand[0].length, 0);
        bot.setHand(hand);

        bot.revealCard(2, 0);
        bot.revealCard(1, 0);
        bot.revealCard(0,1);
        bot.revealCard(1,1);
        bot.revealCard(0,3);
        bot.revealCard(1,3);
        bot.drawConsolHand();
        System.out.println(bot.test());*/
        
    }
}