package game;

/**
 * Represents a single card used in the game.
 * Each card has a name, a numeric value, and a visibility state
 * indicating whether it is currently revealed.
 */
public class Card {

    /** The numerical value associated with this card. */
    private int value;

    /** The name or label of this card. */
    private String name;

    /** True if the card is visible (revealed), false otherwise. */
    private boolean visible;

    /** The color of the card for the UI */
    private String color;

    /**
     * Default constructor.
     */
    public Card() {
        this.value = 0;
        this.name = "Default_name";
        this.visible = false;
        this.color = "blue";
    }

    /**
     * Constructs a card with the specified value and name.
     * The card is hidden by default.
     *
     * @param value the card's numerical value
     * @param name  the name or label of the card
     * @param color the name of the card's color
     */
    public Card(int value, String name, String color) {
        this.value = value;
        this.name = name;
        this.visible = false;
        this.color = color;
    }

    /**
     * Checks whether this card has the same value as another card.
     *
     * @param c the card to compare with
     * 
     * @return true if both cards have the same value, false otherwise
     */
    public boolean isEqual(Card c) {
        return this.value == c.value;
    }

    /**
     * Returns the name of this card.
     *
     * @return the card's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns whether this card is currently visible.
     *
     * @return true if the card is visible, false otherwise
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Reveals this card by setting its visibility to true.
     * 
     * @return no return value
     */
    public void reveal() {
        this.visible = true;
    }

    /**
     * Returns a string representation of the card,
     * including its name, value, and visibility state.
     *
     * @return a string description of the card
     */
    public String toString() {
        return "Card " + this.getName() + " value of: " + this.getName() + "color : " + this.color + ", visible: " + this.isVisible();
    }

    /**
     * Returns the numeric value of this card.
     *
     * @return the card's value
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Return the name of the card's color
     * 
     * @return color's name
     */
    public String getColor(){
        return this.color;
    }
}
