import java.awt.event.WindowEvent;
import java.util.*;

public class Cluedo {
    public Card winSus;
    public Card winWeapon;
    public Card winRoom;

    // public Set<Card> winningCards = new HashSet<Card>();
    public ArrayList<Player> players = new ArrayList<Player>();
    public Board board;


    public Player currentPlayer;
    public GUI gui;
    public boolean suggestedThisTurn;
    ArrayList<Icon> tokens = new ArrayList<Icon>();

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

    public int dice1, dice2, diceTotal;

    public static Cluedo game;

    public static void main(String[] args) {
        game = new Cluedo();
        game.setUp();
    }

    public void setUp() {
        resetFields();
        setUpPlayers();
        board = new Board(players);
        gui = new GUI(game);
        setUpCards();
        currentPlayer = players.get(0);
        gui.updatePlayerDisplay(currentPlayer);
        rollDice();
    }

    public void resetFields() {
        suggestedThisTurn = false;
        tokens = new ArrayList<Icon>();
        players = new ArrayList<Player>();
        allCards = new ArrayList<Card>();
        characterCards = new ArrayList<Card>();
        weaponCards = new ArrayList<Card>();
        roomCards = new ArrayList<Card>();
        toDeal = new ArrayList<Card>();
        gui = null;
        winRoom = null;
        winSus = null;
        winWeapon = null;
    }

    public void endGame() {
        Restart_UI restart = new Restart_UI();
        if (!restart.restart()) {
            return;
        }
        gui.restartClose = true;
        gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
        setUp();
    }

    public void nextPlayer() {
        int index = players.indexOf(currentPlayer);
        if (index == players.size() - 1) {
            currentPlayer = players.get(0);
        } else {
            currentPlayer = players.get(index + 1);
        }
        if (currentPlayer.accused) {
            nextPlayer();
        }
        gui.updatePlayerDisplay(currentPlayer);
        suggestedThisTurn = false;
        gui.canSuggest();
        rollDice();
    }


    /**
     * This method sets up the players
     */
    public void setUpPlayers() {
        Player_Select_UI start = new Player_Select_UI(this);
        players = start.setUp();

        for (Player t : players) {
            this.tokens.add(t.token);
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

        winSus = characterCards.get(0);
        winWeapon = weaponCards.get(0);
        winRoom = roomCards.get(0);

        //Remove winning cards from deal cards
        toDeal.remove(characterCards.get(0));
        toDeal.remove(weaponCards.get(0));
        toDeal.remove(roomCards.get(0));

        //Shuffle and deal cards
        Collections.shuffle(toDeal);
        //System.out.println("Dealing cards...");

        while (!toDeal.isEmpty()) {
            for (Player p : players) {
                p.addCardToHand(toDeal.remove(0));
                if (toDeal.isEmpty()) {
                    break;
                }
            }
        }
        //debug code for printing winning cards
        System.out.println(winSus + " " + winRoom + " " + winWeapon);

    }

    /**
     * SUGGESTION METHOD
     * <p>
     * Runs the suggestion aspect of the game
     * Player inputs Character and Weapon (Room based on player location)
     * then compares the suggestion against other player's card
     * <p>
     * Prints out if the suggestion is refuted or not (Card details are printed if refuted)
     */
    public void suggestion(String suspect, String weapon) {
        String room = board.getPlayerRoom(currentPlayer).name;
       // System.out.println("You're suggesting that it was " + suspect + " with the " + weapon + " in the " + room);
            for(Player s: players){
                if(s != currentPlayer && s.character.equals(suspect)){
                    board.kidnapPlayer(s,board.getPlayerRoom(currentPlayer));
                    break;
                }
            }
            board.moveWeapon(weapon, board.getPlayerRoom(currentPlayer));
            gui.map.repaint();
        
        for (Player s : getSuggestionOrder(currentPlayer)) {
            if (s != currentPlayer) {
                ArrayList<Card> refutingCards = new ArrayList<Card>();
                for (Card c : s.hand) {
                    if (c.name.toLowerCase().equals(suspect.toLowerCase()) || c.name.toLowerCase().equals(weapon.toLowerCase()) || c.name.toLowerCase().equals(room.toLowerCase())) {
                        refutingCards.add(c);
                    }
                }
                if (!refutingCards.isEmpty()) {
                    if (refutingCards.size() == 1) {
                        System.out.println("3");
                        gui.createRefutePanel(s,refutingCards.get(0));
                        return;
                    } else {
                        System.out.println("2");
                        gui.createSelectRefutePanel(s);
                        return;
                    }
                }
            }
        }
        System.out.println("1");
        gui.createNoRefutePanel();
    }


    /**
     * GET SUGGESTION ORDER METHOD
     * <p>
     * Helper method for the suggestion method
     * Takes in the player that made the suggestion
     * returns a list of all other players based on turn order (Excludes current player)
     *
     * @param player current player
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
     * ACCUSATION METHOD
     * <p>
     * Method allows the player to make an accusation
     * Works similar to the suggestion method but room is chosen by player,
     * Does not cycle through other players, and eliminates player if they
     * are wrong.
     *
     * @return boolean based on correctness of the accusation
     */
    public void accusation(String suspect, String weapon, String room) {
        System.out.println("You're accusing " + suspect + " with the " + weapon + " in the " + room);
        System.out.println("You're accusing " + winSus + " with the " + winWeapon + " in the " + winRoom);
        if (suspect.equals(winSus.name) && room.equals(winRoom.name) && weapon.equals(winWeapon.name)) {
            gui.createWinWindow(currentPlayer, suspect, weapon, room);//do win shit - spawn a window and close the game
        } else {
            currentPlayer.accused = true;
            gui.createOutWindow(currentPlayer, suspect, weapon, room);
            currentPlayer.position.player = null;
            gui.map.repaint();
            this.nextPlayer();
            gui.getDiceOutput().setText("Roll the dice");
        }
    }

    public static int rollD6() {
        return (int) (Math.random() * 6 + 1);
    }

    public void rollDice() {
        dice1 = rollD6();
        dice2 = rollD6();
        diceTotal = dice1 + dice2;
        gui.updateDiceOutput(diceTotal);
    }


}

