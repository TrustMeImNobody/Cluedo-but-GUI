import java.util.ArrayList;

public class Room {
    private ArrayList<Cell> cells;
    public String weapon;

    String name;

    public Room (String n){
        this.name = n;
        this.cells = new ArrayList<>();
    }

    /**
     * Add cell methods
     * the following methods can add the cells that are part of the rooms
     * by adding each cell individually to the list of room cells or
     * by setting the full list of cells at once
     * */
    public void addCells(Cell c){
        this.cells.add(c);
    }

    public ArrayList<Cell> getCells(){
        return cells;
    }

    /**
     * Adds the specified weapon to this room, each room has a designated cell for it
     * @param weapon
     */
    public void addWeapon(String weapon){
        switch (name) {
            case "Kitchen":
            case "Dining Room":
            case "Ballroom":
            case "Conservatory":
            case "Lounge":
                cells.get(0).weapon = weapon;
                break;
            case "Hall":
                cells.get(24).weapon = weapon;
                break;
            case "Study":
            case "Library":
                cells.get(cells.size() - 1).weapon = weapon;
                break;
            case "Billiard Room":
                cells.get(11).weapon = weapon;
                break;
        }
        this.weapon = weapon;
    }
}
