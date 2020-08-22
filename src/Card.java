public class Card {
    enum Type{
        WEAPON,
        ROOM,
        CHARACTER
    }
    Type type;
    String name;

    public Card(Type t, String n){
        this.type = t;
        this.name = n;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return  "|" + type + ": " + name + "|";
    }
}
