import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class Cluedo {
    public Card winSus;
    public Card winWeapon;
    public Card winRoom;

   // public Set<Card> winningCards = new HashSet<Card>();
    public ArrayList<Player> players = new ArrayList<Player>();
    private Board board;
    private Boolean gameOn = true;
    
    public Player currentPlayer;
    public Test_GUI gui;
    
    //Array lists of character, weapon and room names
    public static final ArrayList<String> characters = new ArrayList<String>(Arrays.asList("Miss Scarlett", "Colonel Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Professor Plum"));
    public static final ArrayList<String> weapons = new ArrayList<String>(Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"));
    public static final ArrayList<String> rooms = new ArrayList<String>(Arrays.asList("Kitchen", "Ballroom", "Conservatory", "Billiard Room", "Library", "Study", "Hall", "Lounge", "Dining Room"));
    
    //Lists needed for card setup
    public ArrayList<Card> allCards = new ArrayList<Card>();
    public ArrayList<Card> characterCards = new ArrayList<Card>();
    public ArrayList<Card> weaponCards = new ArrayList<Card>();
    public ArrayList<Card> roomCards = new ArrayList<Card>();
    public ArrayList<Card> toDeal = new ArrayList<Card>();

    public static void main(String[] args) throws IOException {
        Cluedo game = new Cluedo();
        game.gui = new Test_GUI(game);
        game.setUpPlayers();
        game.board = new Board(game.players);
        game.setUpCards();
        game.runGame();
    }

    public void runGame() {
        while (gameOn) {
            boolean playerGone = false;
            for (Player player : players) {
                if (player.accused) {
                    continue;
                }
                playerGone = true;
                if (doTurn(player)) {//doTurn returns true if the player won so this handles the winning bit
                    System.out.println(player.character + " wins!");
                    System.out.println("They worked out that the murderer was " + winSus.name + " in the " + winRoom.name + " with the " + winWeapon.name);
                    gameOn = false;
                    break; //break the for loop when someone wins
                }
            }
            if (!playerGone) {
                System.out.println("All of the players have been eliminated, no one wins");
                break;
            }
        }
    }

    /**
     * This method sets up the players
     */
    public void setUpPlayers() {
        int num;
        while (true) {
            System.out.println("How many players do you want in this game?");
            System.out.println("You can have between 3 and 6 players.");
            Scanner in = new Scanner(System.in);
            num = in.nextInt();
            if (num >= 3 && num <= 6) {
                break;
            } else {
                System.out.println("Please choose between 3 and 6 players.");
            }
        }
        for (int i = 0; i < num; i++) {
            this.players.add(new Player(characters.get(i)));
            //Collections.reverse(this.players);
        }
    }

    /**
     * This method sets up the cards, winning cards and assigns cards to each player,
     */
    public void setUpCards() {
        //Setup character cards
        for (String c : characters) {
            Card charCard = new Card(Card.Type.CHARACTER, c);
            allCards.add(charCard);
            characterCards.add(charCard);
        }
        //Setup weapon cards
        for (String w : weapons) {
            Card weapCard = new Card(Card.Type.WEAPON, w);
            allCards.add(weapCard);
            weaponCards.add(weapCard);
        }
        //Setup room cards
        for (String r : rooms) {
            Card roomCard = new Card(Card.Type.ROOM, r);
            allCards.add(roomCard);
            roomCards.add(roomCard);
        }
        toDeal = allCards;

        //Shuffle and pick winning cards
        Collections.shuffle(characterCards);
        Collections.shuffle(weaponCards);
        Collections.shuffle(roomCards);
        
        winSus=characterCards.get(0);
        winWeapon=weaponCards.get(0);
        winRoom=roomCards.get(0);

        //Remove winning cards from deal cards 
        toDeal.remove(characterCards.get(0));
        toDeal.remove(weaponCards.get(0));
        toDeal.remove(roomCards.get(0));

        //Shuffle and deal cards
        Collections.shuffle(toDeal);
        System.out.println("Dealing cards...");

        while (!toDeal.isEmpty()) {
            for (Player p : players) {
                p.addCardToHand(toDeal.get(0));
                toDeal.remove(0);
                if (toDeal.isEmpty()) {
                    break;
                }
            }
        }
        //debug code for printing winning cards
//        for(Card c:winningCards){
//            System.out.println(c.name);
//        }

    }

    /**
     *DO TURN METHOD
     * 
     * Runs the turns for the current player
     * Will read inputs and perform different actions based on those inputs
     * 
     * @param player current player
     *
     * @return boolean based on if current player has completed the wining turn
     */
    public boolean doTurn(Player player) {
        currentPlayer = player;
        board.displayBoard();
        System.out.println(player.character + " it is your turn!");
        ArrayList<Card> hand = player.getHand();
        System.out.println("Your hand consists of:");
        for (Card card : hand) {
            System.out.println(card);
        }
        if (board.getPlayerRoom(player) != null) {
            System.out.println("You are in the " + board.getPlayerRoom(player).name);
        } else {
            System.out.println("You are not currently in any room");
        }
        System.out.println("Do you want to move or make an accusation?");
        System.out.println("Enter 'move' to move or 'accuse' to make an accusation");
        while (true) {
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if (input.toLowerCase().equals("move")) {
                if (move(player)) {
                    System.out.println("You have entered a room, make a suggestion.");
                    suggestion(player);
                }
                System.out.println("You may still make an accusation. Enter 'accuse' to make an accusation or anything else to end your turn.");
                in = new Scanner(System.in);
                input = in.nextLine();
                if (!input.toLowerCase().equals("accuse")) {
                    break;
                }
            }
            if (input.toLowerCase().equals("accuse")) {
//                if (accusation(player)) {
//                    return true;
//                } else {
//                    System.out.println("Your accusation was wrong, you are out\n ");
//                    player.accused = true;
//                }
                break;
            }
            System.out.println("Your input wasn't recognised, please try again");
        }
        return false;
    }

    /**
     *SUGGESTION METHOD
     *
     * Runs the suggestion aspect of the game
     * Player inputs Character and Weapon (Room based on player location)
     * then compares the suggestion against other player's card
     * 
     * Prints out if the suggestion is refuted or not (Card details are printed if refuted)
     *
     * @param player
     * 
     */
    public void suggestion(Player player) {
        ArrayList<Card> hand = player.getHand();
        System.out.println("Your hand consists of:");
        for (Card card : hand) {
            System.out.println(card);
        }
        String suspect = getInputCard("suspect", characters.toString(), characters);
        String weapon = getInputCard("weapon", weapons.toString(), weapons);
        String room = board.getPlayerRoom(player).name;
        System.out.println("You're suggesting that it was " + suspect + " with the " + weapon + " in the " + room);

        for (Player s : getSuggestionOrder(player)) {
            if (s != player) {
                ArrayList<Card> refutingCards = new ArrayList<Card>();
                for (Card c : s.hand) {
                    if (c.name.toLowerCase().equals(suspect.toLowerCase()) || c.name.toLowerCase().equals(weapon.toLowerCase()) || c.name.toLowerCase().equals(room.toLowerCase())) {
                        refutingCards.add(c);
                    }
                }
                if (!refutingCards.isEmpty()) {
                    if (refutingCards.size() == 1) {
                        System.out.println("Player: " + s.character + " has this card to refute your suggestion: " + refutingCards.get(0).name);
                        return;
                    } else {
                        System.out.flush();
                        System.out.println("Player: " + s.getCharacter() + " has cards to refute your suggestion. Get this player to input ok to select a card");
                        Scanner in = new Scanner(System.in);
                        String input = in.next();
                        if (input.toLowerCase().equals("ok")) {
                            String refute = getRefuteCard(suspect, weapon, room, refutingCards);
                            System.out.flush();
                            System.out.println("You suggested that it was " + suspect + " with the " + weapon + " in the " + room);
                            System.out.println("Player: " + s.character + " has this card to refute your suggestion: " + refute);
                            return;
                        }
                    }
                }
            }
        }
        System.out.println("No other player has cards to refute your suggestion.");
    }


    /**
     *GET SUGGESTION ORDER METHOD
     *
     * Helper method for the suggestion method
     * Takes in the player that made the suggestion
     * returns a list of all other players based on turn order (Excludes current player)
     *
     * @param player current player
     *
     * @return list player refuting order
     */
    private ArrayList<Player> getSuggestionOrder(Player player) {//Should exclude player by default
        int index = 7;
        ArrayList<Player> order = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {//Sorry this is horrible
            if (players.get(i).equals(player)) {
                index = i;
            }
            if (i > index) {
                order.add(players.get(i));
            }
        }
        for (int i = 0; i < index; i++) {
            order.add(players.get(i));
        }
        return order;
    }

    /**
     *GET REFUTE CARD METHOD
     *
     * Helper method for the suggestion method
     * Allows the player that has multiple cards that refute the current suggestion
     * to chose which card they reveal to who made the suggestion
     *
     * @param suspect Character that was suggested
     * @param weapon Weapon that was suggested
     * @param suspect Room that was suggested
     * @param refutingCards Cards that refute the suggestion
     *
     * @return string of the card name that player chooses
     */
    public String getRefuteCard(String suspect, String weapon, String room, ArrayList<Card> refutingCards) {
        while (true) {
            System.out.println("The suggestion is: " + suspect + " with the " + weapon + "in the " + room);
            System.out.println("The cards you have that refute is are: " + refutingCards.toString());
            System.out.println("Please select one of these cards by inputting the name. Once you have selected your card, get the player who made the suggestion");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            for (Card c : refutingCards) {
                if (c.getName().toLowerCase().equals(input.toLowerCase())) {
                    return c.getName();
                }
            }
            System.out.println("That wasn't a valid card name");
        }

    }


    /**
     *GET INPUT CARD METHOD
     *
     * Helper method for the suggestion and accusation method
     * Allows the player to choose cards (based on type) they wish to use
     * for suggesting or accusing.
     *
     * @param type of card being selected (Character, Weapon or room)
     * @param listOfPossibilities Options player can pick based on type
     * @param list list of card names of that type
     *
     * @return string of the card name that player chooses
     */
    public String getInputCard(String type, String listOfPossibilities, ArrayList<String> list) {//list and listOfPossibilities could be combind into one but this way seems easier
        while (true) {
            System.out.println("Now please enter a " + type + ":");
            System.out.println("The list of " + type + "s consists of " + listOfPossibilities.toString());
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            if (list.contains(input)) {
                return input;
            } else {
                System.out.println("That wasn't a valid " + type + "  name.");
            }
        }
    }


    /**
     *ACCUSATION METHOD
     *
     * Method allows the player to make an accusation
     * Works similar to the suggestion method but room is chosen by player,
     * Does not cycle through other players, and eliminates player if they
     * are wrong.
     *
     * @return boolean based on correctness of the accusation
     */
    public boolean accusation(String suspect, String weapon, String room) {
        System.out.println("You're accusing " + suspect + " with the " + weapon + " in the " + room);
        System.out.println("TEST You're accusing " + winSus.name + " with the " + winWeapon.name + " in the " + winRoom.name);
        if (suspect.equals(winSus.name) && room.equals(winRoom.name) && weapon.equals(winWeapon.name)) {
            return true;
        }
        return false;
    }

    public static int rollD6(){
        return (int) (Math.random() * 6 + 1);
    }

    /**
     * MOVE METHOD
     *
     * Allows player to move across the board based on
     * their 'Dice roll' 
     *
     * @param player player moving
     *
     * @return boolean based on move success 
     */
    public boolean move(Player player) {
        int dice1 = rollD6();
        int dice2 = rollD6();
        int sum = dice1 + dice2;
        Boolean isInRoom = board.getPlayerRoom(player)!=null;
        System.out.println("You rolled " + sum);
        while (sum > 0) {
            board.displayBoard();
            System.out.println("You can still move " + sum + " tiles.");
            System.out.println("Enter 'u' to move up, 'l' to move left, 'd' to move down or 'r' to move right");
            Scanner in = new Scanner(System.in);
            char input = in.next().charAt(0);
            if  (board.movePlayer(player, input)) {
                sum -= 1;
                Room temp = board.getPlayerRoom(player);
                if (temp != null  && !isInRoom) {
                    board.displayBoard();
                    System.out.println("You are in " + temp.name);
                    return true;
                }
            } else {
                System.out.println("That was not a valid move, please try again");
            }

        }
        board.displayBoard();
        System.out.println("You have finished your move.");
        return false;
    }

}

