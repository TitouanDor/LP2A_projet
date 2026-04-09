package game;

import game.gamerule.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuController implements ActionListener {
    private MenuWindow view;

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

        // button for game rules 
        if (command.equals("Game Rules")) {
            System.out.println("Display of rules");
            RulesWindow rules = new RulesWindow();
            rules.setVisible(true);
            return;
        }

        boolean[] players = view.getSelectedPlayers();
        Skyjo game;

        // instanciate the good model with the clicked button 
        if (command.equals("Short Mode (1 round)")) {
            game = new ShortSkyjo(3, 4, players);
        } else if (command.equals("Invert Mode")) {
            game = new InvertSkyjo(3, 4, players);
        } else if (command.equals("Invert Short Mode (1 round)")) {
            game = new InvertShortSkyjo(3, 4, players);
        } else {
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