import java.util.ArrayList;

public class Player {
    ArrayList<Card> hand;
    Cell position;
    String character;
    String name;
    Icon token;

    public Player(String character){
        this.character = character;
        this.hand = new ArrayList<Card>();
        this.token = new Icon(character);
    }

    public void addCardToHand(Card c){
        this.hand.add(c);
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Cell getPosition() {
        return position;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public String getCharacter() {
        return character;
    }

    @Override
    public String toString() {
        return "Player{" +
                "character =" + character +
                ", position =" + position +
                ", hand ="  + hand +
                '}';
    }
}



