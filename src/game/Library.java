package game;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Represents the card library (deck) used in the game.
 * This class loads cards from a data file, stores them in memory,
 * and provides methods to draw cards randomly or by index.
 */
public class Library {

    /** List of all cards currently available in the library. */
    private ArrayList<Card> list;

    /** Scanner used to read the card data file. */
    private Scanner reader;

    /**
     * Constructs a new Library by loading cards from the file <code>card.dat</code>.
     * If the file cannot be found, an error message is printed to standard error.
     */
    public Library() {
        this.list = new ArrayList<Card>();
        try {
            this.reader = new Scanner(new FileInputStream("./../card.dat"));
            loadCard();
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find the file card.dat: " + e.getMessage());
        }
    }
    
    /**
     * Returns the number of cards remaining in the library.
     *
     * @return total number of cards left
     */
    public int getCardNumber() {
        return this.list.size();
    }

    /**
     * Loads cards from the data file into the library.
     * Lines starting with '#' are treated as comments and ignored.
     * Each valid line is formatted as: <code>number;value;name</code>,
     * where <code>number</code> determines how many identical cards are added.
     * 
     * @return no return value
     */
    private void loadCard() {
        int id_line = 0;
        while (this.reader.hasNextLine()) {
            String line = reader.nextLine().trim();
            
            // We ignore the comment lines or empty ones
            if (line.isEmpty() || line.startsWith("#")) {
                id_line++;
                continue;
            }

            String[] parts = line.split(";");
            
            // file card.dat with 4 column (ex : 5;-2;Montavon;blue )
            if(parts.length == 4){
                try {
                    int count = Integer.parseInt(parts[0]);
                    int value = Integer.parseInt(parts[1]);
                    String name = parts[2];
                    String colorStr = parts[3];

                    CardData profData = new CardData(value, name, colorStr);

                    //create the number for each card (ex there are 5 cards -2)
                    for (int i = 0; i < count; i++) {
                        this.list.add(new Card(profData));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing numbers on line " + id_line);
                }
            } else {
                System.out.println("Line " + id_line + " skipped (wrong format)");
            }
            id_line++;
        }
        this.reader.close(); // close reader 
    }

    /**
     * Displays all cards currently in the library.
     * 
     * @return no return value
     */
    public void showAllCard() {
        for (Card card : this.list) {
            System.out.println(card.toString());
        }
    }

    /**
     * Draws a random card from the library and removes it from the list.
     *
     * @param isReveled if true, the drawn card is revealed immediately
     * 
     * @return the drawn Card object
     */
    public Card drawRandomCard(boolean isReveled) {
        Random rdm = new Random();
        int verif = this.getCardNumber();
        if(verif<0){
            verif = 0;
        }
        int index = rdm.nextInt(verif);
        Card card = this.list.get(index);
        this.list.remove(index);
        if (isReveled) {
            card.reveal();
        }
        return card;
    }

    /**
     * Draws a specific card from the library by index and removes it.
     *
     * @param index index of the card to draw
     * @param isReveled if true, the card is revealed immediately
     * 
     * @return the drawn Card object
     * 
     * @exception index no verification done here
     */
    public Card draw(int index, boolean isReveled) {
        Card card = this.list.get(index);
        this.list.remove(index);
        if (isReveled) {
            card.reveal();
        }
        return card;
    }

    /**
     * Draws a given number of random cards for game setup.
     *
     * @param cardNumber number of cards to draw
     * 
     * @return a list containing the drawn cards
     */
    public ArrayList<Card> drawSetUp(int cardNumber) {
        ArrayList<Card> list = new ArrayList<Card>(cardNumber);
        for (int i = 0; i < cardNumber; i++) {
            list.add(this.drawRandomCard(false));
        }
        return list;
    }
}
