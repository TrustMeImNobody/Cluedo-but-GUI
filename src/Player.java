import java.util.ArrayList;

public class Player {
    ArrayList<Card> hand;
    Cell position;
    String character;
    String name;
    Icon token;
    boolean accused = false;

    public Player(String character, String name) {
        this.character = character;
        this.name = name;
        this.hand = new ArrayList<Card>();
        this.token = new Icon(character);
    }

    public void addCardToHand(Card c) {
        this.hand.add(c);
    }

    @Override
    public String toString() {
        return "Player{" +
                "character =" + character +
                ", position =" + position +
                ", hand =" + hand +
                '}';
    }
}



