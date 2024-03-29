import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player_Select_UI extends JDialog {
    private JDialog f;
    private JTextField purpleName, blueName, greenName, redName, whiteName, mustardName;
    private JRadioButton purpleRadButton, blueRadButton, greenRadButton, redRadButton, whiteRadButton, mustardRadButton;
    private ArrayList<JTextField> fields = new ArrayList<>();
    private ArrayList<Player> players;
    public Cluedo game;

    public Player_Select_UI(Cluedo g) {
        this.game = g;
    }

    /**
     * Sets up the player select dialogue, and waits for the dialog to close before allowing the main program to continue setting
     * up the rest of the game. Each radio box controls whether the text box next to it is active and whether that text box should be
     * considered when the done button is pressed.
     *
     * @return the list of generated players
     */
    public ArrayList<Player> setUp() {
        f = new JDialog(this, "Character Select", true); //this one line of code stops everything until the dialog box closes
        f.setSize(400, 450);
        f.setResizable(false); //don't let the window be resized so the scaling doesn't break

        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        JLabel instruct = new JLabel("Select Players");
        main.add(instruct, BorderLayout.PAGE_START);
        JPanel sub = new JPanel();
        sub.setLayout(new GridLayout(6, 2));

        purpleRadButton = new JRadioButton("Professor Plum");
        purpleRadButton.addActionListener(e -> {
            if (purpleRadButton.isSelected()) {
                purpleName.setEditable(true);
            } else {
                purpleName.setEditable(false);
            }
        });
        sub.add(purpleRadButton);
        purpleName = new JTextField();
        purpleName.setPreferredSize(new Dimension(100, 10));
        purpleName.setEditable(false);
        sub.add(purpleName);

        blueRadButton = new JRadioButton("Mrs. Peacock");
        blueRadButton.addActionListener(e -> {
            if (blueRadButton.isSelected()) {
                blueName.setEditable(true);
            } else {
                blueName.setEditable(false);
            }
        });
        sub.add(blueRadButton);
        blueName = new JTextField();
        blueName.setPreferredSize(new Dimension(100, 20));
        blueName.setEditable(false);
        sub.add(blueName);

        greenRadButton = new JRadioButton("Mr. Green");
        greenRadButton.addActionListener(e -> {
            if (greenRadButton.isSelected()) {
                greenName.setEditable(true);
            } else {
                greenName.setEditable(false);
            }
        });
        sub.add(greenRadButton);
        greenName = new JTextField();
        greenName.setPreferredSize(new Dimension(100, 10));
        greenName.setEditable(false);
        sub.add(greenName);

        redRadButton = new JRadioButton("Miss Scarlett");
        redRadButton.addActionListener(e -> {
            if (redRadButton.isSelected()) {
                redName.setEditable(true);
            } else {
                redName.setEditable(false);
            }
        });
        sub.add(redRadButton);
        redName = new JTextField();
        redName.setPreferredSize(new Dimension(100, 20));
        redName.setEditable(false);
        sub.add(redName);

        whiteRadButton = new JRadioButton("Mrs. White");
        whiteRadButton.addActionListener(e -> {
            if (whiteRadButton.isSelected()) {
                whiteName.setEditable(true);
            } else {
                whiteName.setEditable(false);
            }
        });
        sub.add(whiteRadButton);
        whiteName = new JTextField();
        whiteName.setPreferredSize(new Dimension(100, 20));
        whiteName.setEditable(false);
        sub.add(whiteName);

        mustardRadButton = new JRadioButton("Colonel Mustard");
        mustardRadButton.addActionListener(e -> {
            if (mustardRadButton.isSelected()) {
                mustardName.setEditable(true);
            } else {
                mustardName.setEditable(false);
            }
        });
        sub.add(mustardRadButton);
        mustardName = new JTextField();
        mustardName.setPreferredSize(new Dimension(100, 20));
        mustardName.setEditable(false);
        sub.add(mustardName);
        JButton done = new JButton("Done");
        done.addActionListener(e -> {
            int count = 0;
            for (JTextField f : fields) {
                if (f.isEditable()) {
                    count++; //count the number of active text fields
                    if (f.getText().equals("")) { //prevents the dialog from closing if any of the active name fields are empty
                        JOptionPane.showMessageDialog(f, "Please type a name for all active players");
                        return;
                    }
                }
            }
            if (count < 3) { //if the number of active text fields is less than 3, don't let the dialog close
                JOptionPane.showMessageDialog(f, "You need at least 3 players");
                return;
            }
            //creates the players as chosen by the users
            players = new ArrayList<>();
            if (purpleRadButton.isSelected()) {
                players.add(new Player("Professor Plum", purpleName.getText()));
            }
            if (blueRadButton.isSelected()) {
                players.add(new Player("Mrs. Peacock", blueName.getText()));
            }
            if (greenRadButton.isSelected()) {
                players.add(new Player("Mr. Green", greenName.getText()));
            }
            if (redRadButton.isSelected()) {
                players.add(new Player("Miss Scarlett", redName.getText()));
            }
            if (whiteRadButton.isSelected()) {
                players.add(new Player("Mrs. White", whiteName.getText()));
            }
            if (mustardRadButton.isSelected()) {
                players.add(new Player("Colonel Mustard", mustardName.getText()));
            }
            f.setVisible(false); //close the dialog once finished
        });
        main.add(done, BorderLayout.PAGE_END);
        main.add(sub);

        //throw all the text fields in an array list for use by the action listener
        fields.add(purpleName);
        fields.add(blueName);
        fields.add(greenName);
        fields.add(redName);
        fields.add(whiteName);
        fields.add(mustardName);

        f.setContentPane(main);
        f.setVisible(true);
        return players;
    }
}
