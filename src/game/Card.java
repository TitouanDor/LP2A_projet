package game;

public class Card {
    private int value;
    private String name;
    private boolean visible;

    public Card(){
        this.value = 0;
        this.name = "Default_name";
        this.visible = false;
    }

    public Card(int value, String name){
        this.value = value;
        this.name = name;
        this.visible = false;
    }

    public boolean isEqual(Card c){
        if (this.value == c.value){
            return true;
        }
        return false;
    }

    public String getName(){
        return this.name;
    }

    public boolean isVisible(){
        return this.visible;
    }

    public void reveal(){
        this.visible = true;
    }
}
