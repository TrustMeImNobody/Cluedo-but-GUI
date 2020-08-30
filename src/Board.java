import java.util.ArrayList;

public class Board {
    public ArrayList<ArrayList<Cell>> cells;
    private ArrayList<Player> players;
    private ArrayList<Room> rooms;

    //board is technically 24*24, but 2 starting cells on top make it 24*25

    /**
     * Constructor for board, calls each of the other board building methods in order
     * @param inPlayers the players to be drawn on the board
     */
    public Board(ArrayList<Player> inPlayers){
        buildBoard();
        createRooms();
        players = inPlayers;
        addPlayers();
        addWeapons();
    }

    /**
     * Builds the initial board of cells with external walls
     */
    public void buildBoard(){
        cells = new ArrayList<>();
        //build row 0 as it has only start cells for white and green
        ArrayList<Cell> temp = new ArrayList<>();
        for(int col=0;col<=23;col++){
            if(col==9||col==14){
                temp.add(new Cell(9,0,"   ")); //set the two not wall cells in this row
            }
            else{ //fill the rest of the row with walls
                temp.add(new Cell(col,0,"XXX"));
            }
        }
        cells.add(temp);
        //add the rest of the board
        for(int row=1;row<=24;row++){
            temp = new ArrayList<>();
            for(int col=0;col<=23;col++){
                if(row==1&&(col==6||col==17)){ //add walls on second row
                    temp.add(new Cell(col,row,"XXX"));
                }
                else if(col==0&&(row==6||row==8||row==16||row==18)){ //add walls on board left edge
                    temp.add(new Cell(col,row,"XXX"));
                }
                else if(col==23&&(row==5||row==7||row==13||row==14||row==18||row==20)){ //add walls on right edge
                    temp.add(new Cell(col,row,"XXX"));
                }
                else if(row==24&&(col==6||col==8||col==15||col==17)){ //add walls on bottom edge
                    temp.add(new Cell(col,row,"XXX"));
                }
                else{
                    temp.add(new Cell(col, row, "   "));
                }
            }
            cells.add(temp);
        }
    }

    /**
     * Adds the weapons to the first 6 rooms of the board
     */
    public void addWeapons(){
        int index = 0;
        for(String weapon:Cluedo.weapons){
            rooms.get(index).addWeapon(weapon);
            index++;
        }
    }

    /**
     * Adds the players to the board, with each having their own specified starting cell
     */
    public void addPlayers(){
        Cell cell = null;
        for(Player p:players){
            switch (p.character) {
                case "Mrs. White":
                    cell = cells.get(0).get(9);
                    break;
                case "Mr. Green":
                    cell = cells.get(0).get(14);
                    break;
                case "Mrs. Peacock":
                    cell = cells.get(6).get(23);
                    break;
                case "Colonel Mustard":
                    cell = cells.get(17).get(0);
                    break;
                case "Professor Plum":
                    cell = cells.get(19).get(23);
                    break;
                case "Miss Scarlett":
                    cell = cells.get(24).get(7);
                    break;
            }
            cell.player = p;
            p.position=cell;
        }
    }

    /**
     * Move the specified player one space in the specified direction, returns false if the move is invalid, true if valid
     * @param player the player that's being moved
     * @param direction char representing the movement direction
     */
    public boolean movePlayer(Player player, char direction){
        //find the player
        Cell playerCell = null;
        for(ArrayList<Cell> row:cells){
            for(Cell c:row){
                if(c.player!=null&&c.player.character.equals(player.character)){
                    playerCell = c;
                }
            }
        }
        if(playerCell==null){return false;} //if the player isn't on the board, break from the method
        //find the cell the player is being moved to
        Cell target = null;
        switch(direction){
            case 'u': //move up
                if(playerCell.row==0){return false;} //prevent player trying to move out of bounds
                target = cells.get(playerCell.row-1).get(playerCell.col);
                break;
            case 'd': //move down
                if(playerCell.row==24){return false;} //prevent player trying to move out of bounds
                target = cells.get(playerCell.row+1).get(playerCell.col);
                break;
            case 'l': //move left
                if(playerCell.col==0){return false;} //prevent player trying to move out of bounds
                target = cells.get(playerCell.row).get(playerCell.col-1);
                break;
            case 'r': //move right
                if(playerCell.col==23){return false;} //prevent player trying to move out of bounds
                target = cells.get(playerCell.row).get(playerCell.col+1);
                break;
            default: return false;
        }
        if(!target.type.equals("   ")){return false;} //if target cell is not empty, the move is invalid
        if(target.player!=null){return false;} //can't move into a square occupied by another player
        target.player = playerCell.player;
        playerCell.player = null;
        player.position = target;
        return true;
    }

    /**
     * Used by suggestion to move the player being accused to the specified room
     * @param victim the player to move
     * @param destination the room to move them to
     */
    public void kidnapPlayer(Player victim, Room destination){
        Room locat =  getPlayerRoom(victim);
        if(locat==null||locat.equals(destination)){return;} //player not found or is already in the room, do nothing
        int mid = destination.getCells().size()/2; //find the rough middle of the room
        Cell prev = victim.position; //players previous position
        for(int i=0;i<6;i++){ //6 trys should be enough to find an empty cell in the middle of the room
            Cell targetCell = destination.getCells().get(mid+i);
            if(targetCell.player!=null||!targetCell.weapon.equals("")){ //if cell isn't empty, check next cell
                continue;
            }
            targetCell.player = victim;
            victim.position = targetCell;
            prev.player = null;
            break; //if player was moved, no need to try again
        }
    }

    /**
     * Find the given weapon and brings it to the specified room, swapping the weapons around if both rooms have one
     * @param weapon the weapon to find
     * @param start the room to bring it to
     */
    public void moveWeapon(String weapon, Room start){
        if(start.weapon.equals(weapon)){return;} //if the weapon is already here, do nothing
        for(Room r:rooms){
            if(r.weapon.equals(weapon)){
                if(!r.weapon.equals("")){ //swap weapons if one is currently in this room
                    r.weapon = start.weapon;
                }
                else{ //else remove the weapon from the other room
                    r.weapon = null;
                }
                start.weapon = weapon; //move the weapon to this room
                break;
            }
        }
    }

    /**
     * Display the cells in the board nicely
     */
    public void displayBoard() {
        StringBuilder s = new StringBuilder();
        for(ArrayList<Cell> row:cells){
            for(Cell c:row){
                s.append(c.displayCell());
                //s.append(c);
            }
            s.append("\n");
        }
        System.out.println(s.toString());
    }

    /**
     * Returns what room the player is currently in, returns null if they're not in a room
     * @param p the player to look for
     */
    public Room getPlayerRoom(Player p){
        for(Room r:rooms){
            for(Cell c:r.getCells()){
                if(c.equals(p.position)){
                    return r;
                }
            }
        }
        return null;
    }

    /**
     * Creates each room on the board, setting it's walls and telling each room object what cells it contains
     */
    public void createRooms(){
        rooms = new ArrayList<>();
        //kitchen
        Room kitchen = new Room("Kitchen");
        cells.get(0).get(1).type="XKI";
        cells.get(0).get(2).type="TCH";
        cells.get(0).get(3).type="ENX";
        for(int row=1;row<=6;row++) {
            for (int col = 0; col <= 5; col++) {
                if(row==6&&col==4){
                    //door is not part of the room
                }
                else if(col==5||row==6){ //create rooms walls, leave the door cell open
                    cells.get(row).get(col).type="XXX";
                }
                else{
                    kitchen.addCells(cells.get(row).get(col));
                }
            }
        }
        rooms.add(kitchen);

        //ballroom
        Room ballroom = new Room("Ballroom");
        cells.get(0).get(10).type = "XXB";
        cells.get(0).get(11).type = "ALL";
        cells.get(0).get(12).type = "ROO";
        cells.get(0).get(13).type = "MXX";
        //top 4 cells of the room
        cells.get(1).get(10).type = "XXX";
        cells.get(1).get(13).type = "XXX";
        ballroom.addCells(cells.get(1).get(11));
        ballroom.addCells(cells.get(1).get(12));
        //rest of the room
        for(int row=2;row<=7;row++) {
            for (int col = 8; col <= 15; col++) {
                if(row==2&&(col==11||col==12)||row==7&&(col==9||col==14)||col==8&&row==5||col==15&&row==5){
                    //doors aren't part of rooms
                }
                else if(row==2||row==7||col==8||col==15){
                   cells.get(row).get(col).type="XXX";
                }
                else{
                    ballroom.addCells(cells.get(row).get(col));
               }
            }
        }
        rooms.add(ballroom);

        //conservatory
        Room conservatory = new Room("Conservatory");

        for(int row=1;row<=5;row++) {
            for (int col = 18; col <= 23; col++) {
                if(col==18&&row==4){
                    //doors aren't part of room
                }
                else if(row==5||col==18){
                    cells.get(row).get(col).type="XXX";
                }
                else{
                    conservatory.addCells(cells.get(row).get(col));
                }
            }
        }
        rooms.add(conservatory);
        cells.get(0).get(19).type = "CON";
        cells.get(0).get(20).type = "SER";
        cells.get(0).get(21).type = "VAT";
        cells.get(0).get(22).type = "ORY";

        //dining room
        Room diningRoom = new Room("Conservatory");
        //top 5 cells
        cells.get(9).get(0).type = "XXX";
        cells.get(9).get(1).type = "XXX";
        cells.get(9).get(2).type = "XXX";
        cells.get(9).get(3).type = "XXX";
        cells.get(9).get(4).type = "XXX";
        for(int row=10;row<=15;row++) {
            for (int col = 0; col <= 7; col++) {
                if(row==10&&(col==0||col==1||col==2||col==3)||col==7&&row==12||row==15&&col==6){
                    //doors aren't part of room
                }
                else if(row==10||col==7||row==15){
                    cells.get(row).get(col).type="XXX";
                }
                else{
                    diningRoom.addCells(cells.get(row).get(col));
                }
            }
        }
        //DINING ROOM
        cells.get(9).get(1).type = "DIN";
        cells.get(9).get(2).type = "ING";
        cells.get(9).get(3).type = " RO";
        cells.get(9).get(4).type = "OMX";
        rooms.add(diningRoom);

        //centre room
        Room centre = new Room("Centre");
        for(int row=10;row<=16;row++) {
            for (int col = 10; col <= 14; col++) {
                if(row==10||col==10||col==14||row==16){
                    cells.get(row).get(col).type="XXX";
                }
                else{
                    centre.addCells(cells.get(row).get(col));
                }
            }
        }
        //rooms.add(centre);

        //billiard room
        Room billiard = new Room("Billiard Room");
        for(int row=8;row<=12;row++) {
            for (int col = 18; col <= 23; col++) {
                if(row==12&&col==22||col==18&&row==9) {
                    //doors aren't part of room
                }
                else if(row==8||row==12||col==18){
                    cells.get(row).get(col).type="XXX";
                }
                else{
                    billiard.addCells(cells.get(row).get(col));
                }
            }
        }
        rooms.add(billiard);
        //BILLIARD ROOM
        cells.get(8).get(19).type = "BIL";
        cells.get(8).get(20).type = "LIA";
        cells.get(8).get(21).type = "RD ";
        cells.get(8).get(22).type = "ROO";
        cells.get(8).get(23).type = "MXX";

        //library
        Room library = new Room("Library");
        //left 3 cells
        cells.get(15).get(17).type = "XXX";
        library.addCells(cells.get(16).get(17));
        cells.get(17).get(17).type = "XXX";
        for(int row=14;row<=18;row++) {
            for (int col = 18; col <= 23; col++) {
                if(row==14&&col==20||col==18&&row==16){
                    //doors aren't part of room
                }
                else if(row==14||col==18||row==18){
                    cells.get(row).get(col).type="XXX";
                }
                else {
                    library.addCells(cells.get(row).get(col));
                }
            }
        }
        //LIBRARY
        cells.get(18).get(19).type = "XLI";
        cells.get(18).get(20).type = "BRA";
        cells.get(18).get(21).type = "RYX";
        rooms.add(library);

        //lounge
        Room lounge = new Room("Lounge");
        for(int row=19;row<=24;row++) {
            for (int col = 0; col <= 6; col++) {
                if(row==19&&col==5) {
                    //doors aren't part of room
                }
                else if(row==19||col==6){
                    cells.get(row).get(col).type="XXX";
                }
                else{
                    lounge.addCells(cells.get(row).get(col));
                }
            }
        }
        //LOUNGE
        cells.get(19).get(2).type = "LOU";
        cells.get(19).get(3).type = "NGE";
        rooms.add(lounge);

        //hall
        Room hall = new Room("Hall");
        for(int row=18;row<=24;row++) {
            for (int col = 9; col <= 14; col++) {
                if(row==18&&(col==11||col==12)||col==14&&row==20) {
                    //doors aren't part of room
                }
                else if(col==9||row==18||col==14){
                    cells.get(row).get(col).type="XXX";
                }
                else{
                    hall.addCells(cells.get(row).get(col));
                }
            }
        }
        //HALL
        cells.get(18).get(9).type = "XHA";
        cells.get(18).get(10).type = "LLX";
        rooms.add(hall);

        //study
        Room study = new Room("Study");
        for(int row=21;row<=24;row++) {
            for (int col = 17; col <= 23; col++) {
                if(row==21&&col==18) {
                    //doors aren't part of room
                }
                else if(col==17||row==21){
                    cells.get(row).get(col).type="XXX";
                }
                else{
                    study.addCells(cells.get(row).get(col));
                }
            }
        }
        //STUDY
        cells.get(21).get(20).type = "STU";
        cells.get(21).get(21).type = "DYX";
        rooms.add(study);
    }

}
