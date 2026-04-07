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

    /** Reference to the UTBM data (Prof, specific color, etc.) */
    private CardData data;

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
     * We use CardData 
     */
    public Card(CardData data) {
        this.data = data;
        this.value = data.getValue();
        this.name = data.getName();
        this.visible = false;
        this.data.getColor(); 
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
     * Getter to access the full data (useful for CardView)
     */
    public CardData getData() {
        return this.data;
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
