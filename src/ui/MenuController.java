package ui;

import game.gamerule.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the MenuWindow. It handles user interactions with the menu, such as button clicks, and updates the view accordingly. It also initializes the appropriate game model based on the user's selection and starts the game.
 * The MenuController listens for actions on the menu buttons and responds by either launching the game rules window, quitting the application, or starting a new game with the selected mode and player configurations.
 */
public class MenuController implements ActionListener {
    private MenuWindow view;



    /**
     * Constructor for the MenuController, which takes a reference to the MenuWindow view to interact with it.
     * 
     * @param view the MenuWindow instance to control
     */
    public MenuController(MenuWindow view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        //button for quit the game
        if (command.equals("Quit the game")) {
            System.out.println("Close the programme");
            System.exit(0); // stop the programm 
            return;
        }

        //button for IA level 
        boolean[] humans = this.view.getSelectedPlayers();
        int aiLevel = this.view.getSelectedAILevel();
        game.gamerule.Skyjo game = new game.gamerule.Skyjo(3, 4, humans);
        game.setAiLevel(aiLevel);

        // button for game rules 
        if (command.equals("Game Rules")) {
            System.out.println("Display of rules");
            RulesWindow rules = new RulesWindow();
            rules.setVisible(true);
            return;
        }

        boolean[] players = view.getSelectedPlayers();

        // instanciate the good model with the clicked button 
        if (command.equals("Short Mode (1 round)")) {
            System.out.println("Short Mode selected");
            game = new ShortSkyjo(3, 4, players);
        } else if (command.equals("Invert Mode")) {
            System.out.println("Invert Mode selected");
            game = new InvertSkyjo(3, 4, players);
        } else if (command.equals("Invert Short Mode (1 round)")) {
            System.out.println("Invert Short Mode selected");
            game = new InvertShortSkyjo(3, 4, players);
        } else {
            System.out.println("Classic Mode selected");
            game = new Skyjo(3, 4, players);
        }

        game.setUiActive(true);

        //initialize the game (deal cards to players)
        game.setUpRound();
        
        // start the game 
        GameWindow gameWindow = new GameWindow(game);
        gameWindow.setVisible(true);
        
        view.dispose(); // close the menu
    }
}