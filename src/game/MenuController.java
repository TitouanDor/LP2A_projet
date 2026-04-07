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
            System.out.println("Launch for the short mode");
            game = new ShortSkyjo(3, 4, players);
        } else if (command.equals("Invert Mode")) {
            System.out.println("Launch for the Invert mode");
            game = new InvertSkyjo(3, 4, players);
        } else if (command.equals("Invert Short Mode (1 round)")) {
            System.out.println("Launch for the Invert short mode");
            game = new InvertShortSkyjo(3, 4, players);
        } else {
            System.out.println("Launch for the classic mode");
            game = new Skyjo(3, 4, players);
        }

        view.dispose(); // close the menu
        
        // start the game 
        game.game(); 
    }
}