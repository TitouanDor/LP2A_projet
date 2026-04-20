package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.FontMetrics;


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
    private Color color;

    /**
     * Default constructor.
     */
    public Card() {
        this.value = 0;
        this.name = "empty";
        this.visible = false;
        this.color = Color.GRAY;
    }

    /**
     * Main constructor used by Library.java.
     * @param value The numeric value
     * @param name The professor or card name
     * @param colorStr The color name from the data file
     */
    public Card(int value, String name, String colorStr) {
        this.value = value;
        this.name = name;
        this.visible = false;
        this.color = parseColor(colorStr);
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
    public Color getColor(){
        return this.color;
    }

    /**
     * Parses a color name string and returns the corresponding Color object.
     *
     * @param colorStr the name of the color (e.g., "blue", "red")
     * 
     * @return the Color object corresponding to the given color name, or gray if unknown
     */
    private Color parseColor(String colorStr) {
        switch (colorStr.toLowerCase()) {
            case "blue":   return new Color(54, 154, 194);
            case "aqua":   return new Color(39, 235, 245);
            case "green":  return new Color(38, 189, 45);
            case "yellow": return new Color(213, 230, 46);
            case "red":    return new Color(214, 38, 11);
            default:       return Color.GRAY;
        }
    }

    /**
     * Draws the card on the screen at the specified position and size.
     * 
     * @param g the Graphics object used for drawing
     * @param x the x-coordinate of the top-left corner where the card should be drawn
     * @param y the y-coordinate of the top-left corner where the card should be drawn
     * @param width the width of the card to be drawn
     * @param height the height of the card to be drawn
     */
    public void drawCardUI(Graphics g, int x, int y, int width, int height) {
        int PIXELS_PER_CHARACTER = 10; // Approximate width of each character in pixels
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (visible) {
            // Face up 
            g2.setColor(this.color);
            g2.fillRoundRect(x, y, width, height, 15, 15);
            
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString(String.valueOf(value), x + 10, y + 25);
            
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            FontMetrics fm = g2.getFontMetrics();
            int textX = x + (width - fm.stringWidth(name)) / 2;
            g2.drawString(name, textX, y + height / 2);
        } else {
            // Face down (UTBM style)
            String utbmText = "UTBM";
            g2.setColor(new Color(0, 85, 164)); // Blue UTBM
            g2.fillRoundRect(x, y, width, height, 15, 15);
            g2.setColor(Color.WHITE);
            g2.drawString(utbmText, x + width/2 - PIXELS_PER_CHARACTER*utbmText.length()/2, y + height/2 + PIXELS_PER_CHARACTER/2);
        }
    }
}
