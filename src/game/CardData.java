package game;
public class CardData { } //pour lancer le prog sans soucis 

/* ATTENTION NORMALEMENT CE FICHIER PEUT ETRE SUPPRIMME SI CARD.java EST OK 
package game;

import java.awt.Color;

public class CardData {
    private int value;
    private String name;
    private Color color;

    public CardData(int value, String name, String colorStr) {
        this.value = value;
        this.name = name;
        this.color = parseColor(colorStr);
    }

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

    // Getters for CardView 
    public int getValue() { 
        return value; 
    }
    public String getName() {
        return name; 
    }
    public Color getColor() {
        return color; 
    }
}
*/ 