public class Cell {
    public String type;
    public int col;
    public int row;
    public Player player = null; //by default cell contains no player
    String weapon = "";

    public Cell(int inCol, int inRow, String inType){
        col=inCol;
        row=inRow;
        type=inType;
    }

    /**
     * Displays the contents of the cell as a combination of 3 characters surrounded by {}, empty cells are "{   }", wall cells are "{XXX}"
     * @return
     */
    public String displayCell(){
        if(player!=null){ //if the cell has a player in it, draw them
            switch (player.character) {
                case "Mrs. White":
                    return "{Wht}";
                case "Mr. Green":
                    return "{Grn}";
                case "Mrs. Peacock":
                    return "{Pck}";
                case "Colonel Mustard":
                    return "{Mst}";
                case "Professor Plum":
                    return "{Plm}";
                case "Miss Scarlett":
                    return "{Scr}";
            }
        }
        if(!weapon.equals("")){ //else if it has a weapon, draw that
            switch (weapon) {
                case "Candlestick":
                    return "{Cds}";
                case "Lead Pipe":
                    return "{LdP}";
                case "Revolver":
                    return "{Rev}";
                case "Dagger":
                    return "{Dgr}";
                case "Rope":
                    return "{Rop}";
                case "Spanner":
                    return "{Spn}";
            }
        }
        return "{"+type+"}"; //else draw it's content
    }

    @Override
    public String toString() {
        return "{" +
                row + "," +
                col + "," +
                weapon +
                '}';
    }
}
