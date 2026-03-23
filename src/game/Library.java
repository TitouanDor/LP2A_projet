package game;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Library {

    private ArrayList<Card> list;
    private Scanner reader;

    public int getCardNumber(){
        return this.list.size();
    }

    public Library(){
        this.list = new ArrayList<Card>();
        try {
            this.reader = new Scanner(new FileInputStream("./../card.dat"));
            loadCard();
        } catch (FileNotFoundException e){
            System.err.println("Coudn't find the file card.dat" + e.getMessage());
        }
    }

    private void loadCard(){
        while(this.reader.hasNextLine()){
            String line = reader.nextLine();
            if (!line.startsWith("#")){
                String[] parts = line.split(";");
                int number = Integer.parseInt(parts[0]);
                for(int i = 0; i<number; i++){
                    int value = Integer.parseInt(parts[1]);
                    this.list.add(new Card(value, parts[2]));
                }
            }
        }
    }

    public void showAllCard(){
        for (Card card : this.list) {
            System.out.println(card.toString());
        }
    }

    public Card drawRandomCard(boolean isReveled){
        Random rdm = new Random();
        int index = rdm.nextInt(this.getCardNumber());
        Card card = this.list.get(index);
        this.list.remove(index);
        if (isReveled){
            card.reveal();
        }
        return card;
    }

    public Card draw(int i, boolean isReveled){
        Card card = this.list.get(i);
        this.list.remove(i);
        if (isReveled){
            card.reveal();
        }
        return card;
    }

    public ArrayList<Card> drawSetUp(int cardNumber){
        ArrayList<Card> list = new ArrayList<Card>(cardNumber);
        for (int i = 0;i<cardNumber;i++){
            list.add(this.drawRandomCard(false));
        }
        return list;
    }
}
