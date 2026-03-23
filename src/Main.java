import game.Card;
import game.Library;

public class Main{
    public static void main(String[] args) {
        Card card = new Card();
        System.out.println("Hello world !!" + card.getName());
        Library lib = new Library();
        lib.showAllCard();
        lib.draw(0);
        lib.showAllCard();
    }
}