import game.Card;
import game.Library;

public class Main{
    public static void main(String[] args) {
        Card card = new Card();
        System.out.println("Hello world !!" + card.getName());
        Library lib = new Library();
        System.out.println(lib.getCardNumber());
        card = lib.draw(0, false);
        System.out.println(card.toString());
        lib.drawSetUp(7);
        System.out.println(lib.getCardNumber());
    }
}