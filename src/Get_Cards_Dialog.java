import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class for getting the card input for accusing and suggesting
 */
class Get_Cards_Dialog extends JDialog {
    String suspect = "";
    String weapon = "";
    String location = "";
    private JDialog f;
    public Cluedo game;

    public static final ArrayList<String> weapons = new ArrayList<String>(Arrays.asList("Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"));
    public static final ArrayList<String> rooms = new ArrayList<String>(Arrays.asList("Kitchen", "Ballroom", "Conservatory", "Billiard Room", "Library", "Study", "Hall", "Lounge", "Dining Room"));

    public Get_Cards_Dialog(Cluedo game) {
        this.game = game;
    }

    /**
     *
     * @param list
     * @param string
     * @param accuse if this is being used to get the cards for accusing or for suggesting
     */
    public void setContents(ArrayList<String> list, String string, boolean accuse) {
        f = new JDialog(this,"Choose Suspects",true);
        f.setSize(new Dimension(400,300));
        f.setResizable(false);
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(string);
        newPanel.add(title);

        ButtonGroup button = new ButtonGroup();//you don't actually add this to the panel for some reason
        for (String object : list) {
            JRadioButton rButton = new JRadioButton(object);
            rButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (suspect.equals("")) {
                        suspect = rButton.getText();
                        setContents(weapons, "Please choose a weapon", accuse);
                    } else if (weapon.equals("")) {
                        weapon = rButton.getText();
                        if (accuse) {
                            setContents(rooms, "Please choose a room", accuse);
                        } else {
                            setVisible(false);
                            game.suggestion(suspect, weapon);
                        }
                    } else if (location.equals("")) {
                        location = rButton.getText();
                        setVisible(false);
                        game.accusation(suspect, weapon, location);
                    }
                }
            });
            button.add(rButton);
            newPanel.add(rButton);
        }

        f.setContentPane(newPanel);
        f.setVisible(true);
        f.pack();

        //this.setVisible(false);
    }
}