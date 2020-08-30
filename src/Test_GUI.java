import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

//We will probably want to run the program from here and create a cluedo object in main
public class Test_GUI extends JFrame {

    public final static int windowHeight = 800;
    public final static int windowWidth = 700;

    public Cluedo game;
    public Map map;

    public Test_GUI(Cluedo g) throws IOException {
        this.game = g;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//update this at some point to open a dialog box
        this.setResizable(false);
        JMenuBar bar = createMenuBar();
        this.setJMenuBar(bar);

        JPanel backgroundPanel = new JPanel();

        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.PAGE_AXIS));

        JPanel map = createMapPanel();
        backgroundPanel.add(map);

        JPanel interactPanel = new JPanel();
        interactPanel.setLayout(new BoxLayout(interactPanel, BoxLayout.X_AXIS));

        JPanel controlPanel = createControlPanel();
        interactPanel.add(controlPanel);

        JPanel text = createTextPanel();
        interactPanel.add(text);
        backgroundPanel.add(interactPanel);
        this.add(backgroundPanel);

        this.setVisible(true);
        //frame.pack();//Not necessary if we explicitly define the sizes
        this.setSize(windowWidth, windowHeight);

        //frame.addWindowListener(new WindowAdapter() { //can probably adjust this code to open a dialog when the windo opens
        //				@Override
        //				public void windowOpened(WindowEvent e) {
        //					display.requestFocus();
        //				}
        //			});
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Game");
        JMenuItem i1 = new JMenuItem("Restart Game");
        menu.add(i1);
        menuBar.add(menu);
        return menuBar;
    }

    public JPanel createMapPanel() throws IOException {
        JPanel panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setSize(windowWidth, 458);
        this.map = new Map(this.game);
        panel.add(map);
        return panel;
    }

    public JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setMaximumSize(new Dimension(windowWidth / 2 - 5, windowHeight / 3));
        panel.setPreferredSize(new Dimension(windowWidth / 2 - 5, windowHeight / 3));
        panel.setMinimumSize(new Dimension(windowWidth / 2 - 5, windowHeight / 3));
        //  panel.setLayout(new GridLayout(2,2));
        //panel.add(new JPanel());
        panel.setLayout(new GridLayout(2, 1));
        panel.add(createButtonPanel());
        panel.add(createDPad());
        return panel;
    }

    public JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        //panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setLayout(new GridLayout(2, 1));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        panel.add(createDicePanel());
        panel.add(createAccusePanel());
        //panel.add(suggest);
        return panel;
    }

    private JLabel diceOutput;

    public JPanel createDicePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JButton roller = new JButton("Roll Dice");
        roller.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int d1 = Cluedo.rollD6();
                int d2 = Cluedo.rollD6();
                diceOutput.setText("Test " + d1 + " + " + d2 + " = " + (d1 + d2));
            }
        });
        panel.add(roller);
        diceOutput = new JLabel("Dice output\n here");
        panel.add(diceOutput);
        return panel;
    }

    public JPanel createAccusePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JButton endTurn = new JButton("End Turn");
        endTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //game.endCurrentTurn();
                //todo right the method to end the current turn
            }
        });
        panel.add(endTurn);
        JButton accuse = new JButton("Accuse");
        accuse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //updatePlayerDisplay(createTestPlayer());
                Accuse_Dialog dialog = new Accuse_Dialog(game);
                dialog.setContents(Cluedo.characters, "You are making an accusation. Please choose a suspect:");

            }
        });
        panel.add(accuse);

        return panel;
    }

    private ArrayList<JTextPane> displayHand;
    private JLabel displayPlayer;

    public JPanel createTextPanel() {
        JPanel panel = new JPanel();
        panel.setSize(windowWidth / 2, windowHeight / 3);
        //panel.setBackground(Color.red);
        panel.setLayout(new BorderLayout());
        displayPlayer = new JLabel();
        displayPlayer.setText("Player here");
        displayPlayer.setBorder(BorderFactory.createLineBorder(Color.black, 5));
        panel.add(displayPlayer, BorderLayout.PAGE_START);
        panel.add(createCardPanel());
        panel.setMaximumSize(new Dimension(windowWidth / 2 - 8, windowHeight / 3));
        panel.setPreferredSize(new Dimension(windowWidth / 2 - 8, windowHeight / 3));
        panel.setMinimumSize(new Dimension(windowWidth / 2 - 8, windowHeight / 3));
        return panel;
    }

    public void updatePlayerDisplay(Player p) {
        if (p == null || p.hand == null) {
            throw new Error("Player broke");
        }
        displayPlayer.setText(p.name + " as " + p.character);
        for (JTextPane pane : displayHand) {
            pane.setText("Empty");
        }
        for (int i = 0; i < p.hand.size(); i++) {
            displayHand.get(i).setText(p.hand.get(i).type + "\n" + p.hand.get(i).name);
        }
    }

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

        panel.add(new JLabel(""));
        //up.setHorizontalTextPosition();

        panel.add(up);
        panel.add(new JLabel(""));
        panel.add(left);
        panel.add(new JLabel(""));
        panel.add(right);
        panel.add(new JLabel(""));
        panel.add(down);
        panel.add(new JLabel(""));

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                int x = mouseEvent.getX();
                int y = mouseEvent.getY();
                //System.out.println("x:"+x+" y:"+y);
                if (y < 45 && 100 < x && x < 230) {
                    if(game.board.movePlayer(game.currentPlayer,'u')){
                        map.repaint();
                    }
                }
                if (x < 115 && 47 < y && y < 86) {
                    if(game.board.movePlayer(game.currentPlayer,'l')){
                        map.repaint();
                    }
                }
                if (230 < x && x < 340 && 47 < y && y < 87) {
                    if(game.board.movePlayer(game.currentPlayer,'r')){
                        map.repaint();
                    }
                }
                if (y > 88 && 100 < x && x < 230) {
                    if(game.board.movePlayer(game.currentPlayer,'d')){
                        map.repaint();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
            }
        });
        //panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, false));
        return panel;
    }

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


