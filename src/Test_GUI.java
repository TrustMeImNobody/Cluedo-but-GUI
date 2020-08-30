import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Test_GUI extends JFrame {

    public final static int windowHeight = 800;
    public final static int windowWidth = 700;

    //fields to allow action listeners to update the GUI
    private JLabel diceOutput;
    private ArrayList<JTextPane> displayHand;
    private JLabel displayPlayer;
    private JFrame f;

    public Cluedo game;
    public Map map;

    public boolean restartClose = false;

    /**
     * Constructor, creates the background frame and calls methods to build the rest of the GUI
     * @param g
     */
    public Test_GUI(Cluedo g){
        this.game = g;
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(false); //don't let the window be resized so scaling can't break
        f = this;

        //the menu bar, self explanatory
        JMenuBar bar = createMenuBar();
        this.setJMenuBar(bar);

        //panel for displaying everything
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.PAGE_AXIS));

        //panel for displaying the game board
        JPanel map = createMapPanel();
        backgroundPanel.add(map);

        //the panel that occupies the area below the map
        JPanel interactPanel = new JPanel();
        interactPanel.setLayout(new BoxLayout(interactPanel, BoxLayout.X_AXIS));

        //the left half of the panel below the map
        JPanel controlPanel = createControlPanel();
        interactPanel.add(controlPanel);

        //the right half of the panel below the map
        JPanel text = createTextPanel();
        interactPanel.add(text);

        //display everything on the frame
        backgroundPanel.add(interactPanel);
        this.add(backgroundPanel);
        this.setVisible(true);
        this.setSize(windowWidth, windowHeight);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(restartClose){ //don't bother with the confirmation dialogue if resetting window
                    f.setVisible(false);
                    return;
                }
                if (JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to quit the game?", "Quit Game?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Creates the menu bar
     */
    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem i1 = new JMenuItem("Restart Game");
        //todo action listener on this
        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.endGame();
            }
        });
        menu.add(i1);
        menuBar.add(menu);
        return menuBar;
    }

    /**
     * Creates the map by creating a new map object and adding it to a panel
     */
    public JPanel createMapPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setSize(windowWidth, 458);
        this.map = new Map(this.game);
        panel.add(map);
        return panel;
    }

    /**
     * Creates the left half of the lower panel, stores all the interact elements of the GUI
     * @return
     */
    public JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //force the panels size so it takes up half the screen to prevent the layout manager deciding to ruin everything
        panel.setMaximumSize(new Dimension(windowWidth / 2 - 5, windowHeight / 3));
        panel.setPreferredSize(new Dimension(windowWidth / 2 - 5, windowHeight / 3));
        panel.setMinimumSize(new Dimension(windowWidth / 2 - 5, windowHeight / 3));
        panel.setLayout(new GridLayout(2, 1));
        panel.add(createButtonPanel());
        panel.add(createDPad());
        return panel;
    }

    /**
     * Creates the panel that all the buttons will be added to
     * @return
     */
    public JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        panel.add(createDicePanel());
        panel.add(createAccusePanel());
        return panel;
    }

    /**
     * Creates the panel that holds the button for rolling the dice and displaying the result of the dice roll
     * Also shows how much movement the player has left
     */
    public JPanel createDicePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JButton roller = new JButton("Roll Dice");
        roller.setBorder(BorderFactory.createLineBorder(Color.black,1));
        //Action listener for rolling the dice and displaying the roll. Also prevents the player from rerolling on their turn
        roller.addActionListener(actionEvent -> {
            if(game.diceRolled){
                JOptionPane.showMessageDialog(null, "You've already rolled the dice");
                return;
            }
            game.rollDice();
            diceOutput.setText("You rolled " + game.dice1 + " and " + game.dice2 + " for a total of " + (game.dice1+game.dice2));
        });
        panel.add(roller);
        diceOutput = new JLabel("Roll the dice");
        panel.add(diceOutput);
        return panel;
    }

    /**
     * Creates the panel for the accuse and end turn buttons
     * @return
     */
    public JPanel createAccusePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JButton accuse = new JButton("Accuse");
        accuse.setBorder(BorderFactory.createLineBorder(Color.black,1));
        //Action listener to call the accuse UI to trigger
        accuse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Accuse_Dialog dialog = new Accuse_Dialog(game);
                dialog.setContents(Cluedo.characters, "You are making an accusation. Please choose a suspect:");

            }
        });
        panel.add(accuse);

        JButton endTurn = new JButton("End Turn");
        endTurn.setBorder(BorderFactory.createLineBorder(Color.black,1));
        //Action listener to end the players turn
        endTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //game.endCurrentTurn();
                game.diceRolled = false;
                game.nextPlayer();
                diceOutput.setText("Roll the dice");
            }
        });
        panel.add(endTurn);

        return panel;
    }

    /**
     * Creates the bottom right panel of the GUI, can't be interacted with by the user
     * Displays information about the current player and their hand
     * @return
     */
    public JPanel createTextPanel() {
        JPanel panel = new JPanel();
        panel.setSize(windowWidth / 2, windowHeight / 3);
        panel.setLayout(new BorderLayout());
        displayPlayer = new JLabel();
        displayPlayer.setText("Player here");
        displayPlayer.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        panel.add(displayPlayer, BorderLayout.PAGE_START);
        panel.add(createCardPanel());
        //force the panels size so it takes up half the screen to prevent the layout manager deciding to ruin everything
        panel.setMaximumSize(new Dimension(windowWidth / 2 - 8, windowHeight / 3));
        panel.setPreferredSize(new Dimension(windowWidth / 2 - 8, windowHeight / 3));
        panel.setMinimumSize(new Dimension(windowWidth / 2 - 8, windowHeight / 3));
        return panel;
    }

    /**
     * Updates the bottom right panel of the GUI with the current player's information
     * @param p
     */
    public void updatePlayerDisplay(Player p) {
        if (p == null || p.hand == null) {
            throw new Error("Player broke");
        }
        displayPlayer.setText(p.name + " as " + p.character);
        displayPlayer.setOpaque(true);
        displayPlayer.setBackground(p.token.color);
        for (JTextPane pane : displayHand) {
            pane.setText("Empty");
        }
        for (int i = 0; i < p.hand.size(); i++) {
            displayHand.get(i).setText(p.hand.get(i).type + "\n" + p.hand.get(i).name);
        }
    }

    /**
     * Create the panel that will display the current players cards
     * @return
     */
    public JPanel createCardPanel() {
        displayHand = new ArrayList<>();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3));
        for (int i = 0; i < 6; i++) {
            displayHand.add(new JTextPane());
        }
        for (JTextPane p : displayHand) {
            p.setText("Empty");
            p.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
            panel.add(p);
        }
        return panel;
    }

    /**
     * Creates the dpad on the bottom right of the screen
     * The dpad uses a mouse listener to detect which direction the player wants to move in
     * @return
     */
    public JPanel createDPad() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        JLabel up = new JLabel("Up", SwingConstants.CENTER);
        up.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel left = new JLabel("Left", SwingConstants.CENTER);
        left.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel right = new JLabel("Right", SwingConstants.CENTER);
        right.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel down = new JLabel("Down", SwingConstants.CENTER);
        down.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        panel.add(new JLabel("")); //empty label for spacing
        panel.add(up);
        panel.add(new JLabel("")); //empty label for spacing
        panel.add(left);
        panel.add(new JLabel("")); //empty label for spacing
        panel.add(right);
        panel.add(new JLabel("")); //empty label for spacing
        panel.add(down);
        panel.add(new JLabel("")); //empty label for spacing

        //the mouse listener that determines which of the labels the player has clicked
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                //boilerplate code
            }
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                //boilerplate code
            }
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if(!game.diceRolled){  //if the players hasn't rolled the dice, tell them and don't let them move
                    JOptionPane.showMessageDialog(null, "Roll the dice");
                    return;
                }
                if(game.diceTotal<=0){ //if the player no longer has any movement, tell them and don't let them move
                    JOptionPane.showMessageDialog(null, "You have used up all of your movement");
                    return;
                }
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();
                if (y < 45 && 100 < x && x < 230) {  //player clicked up label
                    if(game.board.movePlayer(game.currentPlayer,'u')){
                        game.diceTotal--;
                        diceOutput.setText("You have "+game.diceTotal+" movement remaining");
                        map.repaint();
                    }
                }
                if (x < 115 && 47 < y && y < 86) { //player clicked left label
                    if(game.board.movePlayer(game.currentPlayer,'l')){
                        game.diceTotal--;
                        diceOutput.setText("You have "+game.diceTotal+" movement remaining");
                        map.repaint();
                    }
                }
                if (230 < x && x < 340 && 47 < y && y < 87) {  //player clicked right label
                    if(game.board.movePlayer(game.currentPlayer,'r')){
                        game.diceTotal--;
                        diceOutput.setText("You have "+game.diceTotal+" movement remaining");
                        map.repaint();
                    }
                }
                if (y > 88 && 100 < x && x < 230) { //player clicked down label
                    if(game.board.movePlayer(game.currentPlayer,'d')){
                        game.diceTotal--;
                        diceOutput.setText("You have "+game.diceTotal+" movement remaining");
                        map.repaint();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                //boilerplate code
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                //boilerplate code
            }
        });
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, false));
        return panel;
    }

    /**
     * Method used for debugging player information display area
     * @return
     */
    public Player createTestPlayer() {
        Player test = new Player(Cluedo.characters.get(0), "Test");
        test.name = "Testing with a ridiculous name";
        test.addCardToHand(new Card(Card.Type.WEAPON, Cluedo.weapons.get(0)));
        test.addCardToHand(new Card(Card.Type.ROOM, Cluedo.rooms.get(3)));
        test.addCardToHand(new Card(Card.Type.CHARACTER, "card3"));
        test.addCardToHand(new Card(Card.Type.WEAPON, "card4"));
        test.addCardToHand(new Card(Card.Type.ROOM, "card5"));
        test.addCardToHand(new Card(Card.Type.CHARACTER, "card6"));
        return test;
    }

    public void createWinWindow(Player player, String suspect, String weapon, String room) {
        JDialog dialog = new JDialog();
        dialog.getContentPane().setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.PAGE_AXIS));
        JPanel panel = (JPanel) dialog.getContentPane();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel topPanel = new JPanel();
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        String string = player.character + " (" + player.name + ")" + " wins! They deduced that it was "
                + suspect + " with the " + weapon + " in the " + room + ". Thank you for playing Cluedo!";
        JLabel title = new JLabel(string);
        topPanel.add(title);

        panel.add(topPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button1 = new JButton("Play again");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Do Carl's restart thing
            }
        });
        bottomPanel.add(button1);

        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton button2 = new JButton("Quit");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        bottomPanel.add(button2);

        panel.add(bottomPanel);

        dialog.setVisible(true);
        dialog.pack();
    }

    public void createOutWindow(Player player, String suspect, String weapon, String room) {
        JDialog dialog = new JDialog();
        dialog.getContentPane().setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.PAGE_AXIS));
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = (JPanel) dialog.getContentPane();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel topPanel = new JPanel();
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        String string = player.character + " (" + player.name + ")" + " is out! Their accusation that it it was "
                + suspect + " with the " + weapon + " in the " + room + " was incorrect!";
        JLabel title = new JLabel(string);
        topPanel.add(title);

        panel.add(topPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button1 = new JButton("Continue");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dialog.dispose();
            }
        });
        bottomPanel.add(button1);

        panel.add(bottomPanel);

        dialog.setVisible(true);
        dialog.pack();
    }

    /**
     * Triggers if all players have been eliminated
     * could probably be merged with createWinWindow
     */
    public void createLoseWindow() {
        JDialog dialog = new JDialog();
        dialog.getContentPane().setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.PAGE_AXIS));
        JPanel panel = (JPanel) dialog.getContentPane();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel topPanel = new JPanel();
        topPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        String string ="All players have been eliminated. Thank you for playing Cluedo!";
        JLabel title = new JLabel(string);
        topPanel.add(title);

        panel.add(topPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton button1 = new JButton("Play again");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Do Carl's restart thing
            }
        });
        bottomPanel.add(button1);

        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        JButton button2 = new JButton("Quit");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });
        bottomPanel.add(button2);

        panel.add(bottomPanel);

        dialog.setVisible(true);
        dialog.pack();
    }


}


